-- EXTENSIONES Y ESQUEMA
-- Generación de UUID --> mas seguro que SERIAL

CREATE EXTENSION IF NOT EXISTS pgcrypto;

-- Emails case-insensitive
CREATE EXTENSION IF NOT EXISTS citext;

-- Esquema para aislar objetos de la app y no trabajar sobre public
CREATE SCHEMA IF NOT EXISTS ecommerce;


SET search_path TO ecommerce, public;


-- ENUMS (tipos controlados), los declararemos posteriormente en el backend y en el front con TS

-- Estados reutilizables
CREATE TYPE ecommerce.status_general AS ENUM ('ACTIVE', 'INACTIVE', 'SUSPENDED', 'DELETED');

-- Tipo de documento
CREATE TYPE ecommerce.document_type AS ENUM ('CI', 'DNI', 'PASSPORT', 'OTHER');

-- Estado del pedido
CREATE TYPE ecommerce.order_status AS ENUM ('CREATED', 'PAID', 'PREPARING', 'SHIPPED', 'DELIVERED', 'CANCELLED');

-- Estado del pago
CREATE TYPE ecommerce.payment_status AS ENUM ('PENDING', 'APPROVED', 'REJECTED', 'VOIDED', 'REFUNDED');

-- Método de pago
CREATE TYPE ecommerce.payment_method AS ENUM ('CARD', 'BANK_TRANSFER', 'CASH', 'PAYPAL', 'MERCADOPAGO', 'OTHER');

-- Método de envío
CREATE TYPE ecommerce.shipping_method AS ENUM ('PICKUP', 'COURIER', 'POSTAL', 'CARRIER', 'OTHER');

-- Estado del envío
CREATE TYPE ecommerce.shipment_status AS ENUM ('PENDING', 'DISPATCHED', 'IN_TRANSIT', 'DELIVERED', 'CANCELLED');

-- Monedas soportadas
CREATE TYPE ecommerce.currency_code AS ENUM ('UYU', 'USD');


-- SEGURIDAD / IDENTIDAD: ROLE, USER

-- Roles del sistema (admin, customer, etc.)
CREATE TABLE ecommerce.role (
                                role_id     UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                                name        VARCHAR(80) NOT NULL UNIQUE,
                                description TEXT,
                                status      status_general NOT NULL DEFAULT 'ACTIVE',
                                created_at  TIMESTAMPTZ NOT NULL DEFAULT now()
);

-- Usuarios de la aplicación
CREATE TABLE ecommerce.app_user (
                                    user_id          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                                    first_name       VARCHAR(80) NOT NULL,
                                    middle_name      VARCHAR(80),
                                    last_name        VARCHAR(80) NOT NULL,
                                    second_last_name VARCHAR(80) NOT NULL,
                                    email            citext NOT NULL UNIQUE,
                                    password_hash    TEXT NOT NULL, -- solo hash, nunca la contraseña en claro
                                    document_type    document_type NOT NULL,
                                    document_number  VARCHAR(30) NOT NULL,
                                    status           status_general NOT NULL DEFAULT 'ACTIVE',
                                    created_at       TIMESTAMPTZ NOT NULL DEFAULT now(),
                                    updated_at       TIMESTAMPTZ,
                                    last_login_at    TIMESTAMPTZ,
                                    role_id          UUID NOT NULL REFERENCES role(role_id),
                                    CONSTRAINT uq_app_user_document UNIQUE (document_type, document_number)
);



-- COUNTRY -> REGION -> CITY -> ADDRESS, USER_ADDRESS

-- País
CREATE TABLE ecommerce.country (
                                   country_id  UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                                   name        VARCHAR(80) NOT NULL UNIQUE,
                                   phone_code  VARCHAR(8),                  -- Ej: +598
                                   status      status_general NOT NULL DEFAULT 'ACTIVE'
);

-- Región / Departamento
CREATE TABLE ecommerce.region (
                                  region_id   UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                                  country_id  UUID NOT NULL REFERENCES ecommerce.country(country_id),
                                  name        VARCHAR(80) NOT NULL,
                                  status      status_general NOT NULL DEFAULT 'ACTIVE',
                                  CONSTRAINT uq_region UNIQUE (country_id, name)
);

-- Ciudad
CREATE TABLE ecommerce.city (
                                city_id    UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                                region_id  UUID NOT NULL REFERENCES ecommerce.region(region_id),
                                name       VARCHAR(80) NOT NULL,
                                status     status_general NOT NULL DEFAULT 'ACTIVE',
                                CONSTRAINT uq_city UNIQUE (region_id, name)
);

-- Dirección (separada para permitir múltiples direcciones por usuario)
CREATE TABLE ecommerce.address (
                                   address_id     UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                                   city_id        UUID NOT NULL REFERENCES ecommerce.city(city_id),
                                   street         VARCHAR(120) NOT NULL,
                                   street_number  VARCHAR(20) NOT NULL,
                                   apartment      VARCHAR(20) ,
                                   label          VARCHAR(60) NOT NULL,   -- "Casa", "Trabajo"
                                   postal_code    VARCHAR(12) NOT NULL,
                                   status         ecommerce.status_general NOT NULL DEFAULT 'ACTIVE'
);

-- Relación N:M usuario <-> direcciones
CREATE TABLE ecommerce.user_address (
                                        user_id     UUID NOT NULL REFERENCES ecommerce.app_user(user_id) ON DELETE CASCADE,
                                        address_id  UUID NOT NULL REFERENCES ecommerce.address(address_id) ON DELETE CASCADE,
                                        PRIMARY KEY (user_id, address_id)
);



-- CATÁLOGO: CATEGORÍA, PRODUCTO, VARIANTE, INVENTARIO, IMÁGENES

-- Categoría de producto
CREATE TABLE ecommerce.product_category (
                                            category_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                                            name        VARCHAR(80) NOT NULL UNIQUE,
                                            status      ecommerce.status_general NOT NULL DEFAULT 'ACTIVE',
                                            notes       TEXT NOT NULL
);

-- Producto (ficha general; no guarda talle/color)
CREATE TABLE ecommerce.product (
                                   product_id   UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                                   category_id  UUID NOT NULL REFERENCES ecommerce.product_category(category_id),
                                   brand        VARCHAR(80) NOT NULL,
                                   name         VARCHAR(120) NOT NULL,
                                   description  TEXT,
                                   base_price   NUMERIC(12,2) NOT NULL CHECK (base_price >= 0),
                                   season       VARCHAR(40),
                                   status       status_general NOT NULL DEFAULT 'ACTIVE',
                                   CONSTRAINT uq_product_brand_name UNIQUE (brand, name)
);

-- Variantes (color/talle) - lo vendible real
CREATE TABLE ecommerce.product_variant (
                                           variant_id  UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                                           product_id  UUID NOT NULL REFERENCES ecommerce.product(product_id) ON DELETE CASCADE,
                                           color       VARCHAR(40) NOT NULL,
                                           size        VARCHAR(20) NOT NULL,
                                           sku         VARCHAR(64) NOT NULL UNIQUE,  -- Identificador comercial
                                           barcode     VARCHAR(32),
                                           status      status_general NOT NULL DEFAULT 'ACTIVE',
                                           CONSTRAINT uq_variant_combo UNIQUE (product_id, color, size),
    -- Si hay barcode
                                           CONSTRAINT uq_variant_barcode UNIQUE (barcode)
);


-- Inventario / stock (1:1 con variante)
CREATE TABLE ecommerce.inventory (
                                     inventory_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                                     variant_id   UUID NOT NULL UNIQUE REFERENCES ecommerce.product_variant(variant_id) ON DELETE CASCADE,
                                     quantity     INT NOT NULL DEFAULT 0 CHECK (quantity >= 0),
                                     reserved     INT NOT NULL DEFAULT 0 CHECK (reserved >= 0),
                                     status       status_general NOT NULL DEFAULT 'ACTIVE',
    -- reserved no puede superar quantity
                                     CONSTRAINT ck_inventory_reserved CHECK (reserved <= quantity)
);

-- Imágenes del producto (galería)
CREATE TABLE ecommerce.product_image (
                                         image_id     UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                                         product_id   UUID NOT NULL REFERENCES ecommerce.product(product_id) ON DELETE CASCADE,
                                         url          TEXT NOT NULL,
                                         is_primary   BOOLEAN NOT NULL DEFAULT false,
                                         status       status_general NOT NULL DEFAULT 'ACTIVE',
                                         created_at   TIMESTAMPTZ NOT NULL DEFAULT now(),
                                         description  TEXT
);

-- Solo una imagen principal por producto
CREATE UNIQUE INDEX uq_product_primary_image
    ON ecommerce.product_image(product_id)
    WHERE is_primary = true;


-- VENTAS: PEDIDO, ITEMS, PAGOS

-- Pedido del cliente
CREATE TABLE ecommerce.customer_order (
                                          order_id          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                                          order_number      VARCHAR(30) NOT NULL UNIQUE, -- Visible para el cliente
                                          user_id           UUID NOT NULL REFERENCES ecommerce.app_user(user_id),
                                          status            ecommerce.order_status NOT NULL DEFAULT 'CREATED',
                                          currency          ecommerce.currency_code NOT NULL DEFAULT 'UYU',
                                          notes             TEXT,
                                          subtotal          NUMERIC(12,2) NOT NULL CHECK (subtotal >= 0),
                                          discount_total    NUMERIC(12,2) NOT NULL DEFAULT 0 CHECK (discount_total >= 0),
                                          total_amount      NUMERIC(12,2) NOT NULL CHECK (total_amount >= 0),
                                          created_at        TIMESTAMPTZ NOT NULL DEFAULT now(),
                                          status_updated_at TIMESTAMPTZ
);


-- Detalle de pedido (referencia a la variante)
CREATE TABLE ecommerce.order_item (
                                      order_item_id  UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                                      order_id       UUID NOT NULL REFERENCES ecommerce.customer_order(order_id) ON DELETE CASCADE,
                                      line_number    INT NOT NULL,
                                      variant_id     UUID NOT NULL REFERENCES ecommerce.product_variant(variant_id),
    -- Snapshot (para historial)
                                      sku_snapshot   VARCHAR(64) NOT NULL,
                                      color_snapshot VARCHAR(40) NOT NULL,
                                      size_snapshot  VARCHAR(20) NOT NULL,
                                      unit_price     NUMERIC(12,2) NOT NULL CHECK (unit_price >= 0),
                                      quantity       INT NOT NULL CHECK (quantity > 0),
                                      discount       NUMERIC(12,2) NOT NULL DEFAULT 0 CHECK (discount >= 0),
                                      line_total     NUMERIC(12,2) NOT NULL CHECK (line_total >= 0),
                                      CONSTRAINT uq_order_item_line UNIQUE (order_id, line_number)
);


-- Pagos (permitimos múltiples intentos por pedido, por si falla, etc, no permitimos que se pague 2 veces desde codigo)
CREATE TABLE ecommerce.payment (
                                   payment_id        UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                                   order_id          UUID NOT NULL REFERENCES ecommerce.customer_order(order_id) ON DELETE CASCADE,
                                   transaction_ref   VARCHAR(80) NOT NULL UNIQUE, -- referencia de pasarela
                                   status            ecommerce.payment_status NOT NULL DEFAULT 'PENDING',
                                   method            ecommerce.payment_method NOT NULL,
                                   currency          ecommerce.currency_code NOT NULL DEFAULT 'UYU',
                                   amount_total      NUMERIC(12,2) NOT NULL CHECK (amount_total >= 0),
                                   installments      SMALLINT CHECK (installments IS NULL OR installments >= 1),
                                   installment_amount NUMERIC(12,2) CHECK (installment_amount IS NULL OR installment_amount >= 0),
                                   card_last4        CHAR(4) CHECK (card_last4 IS NULL OR card_last4 ~ '^[0-9]{4}$'),
  paid_at           TIMESTAMPTZ,
  created_at        TIMESTAMPTZ NOT NULL DEFAULT now(),
  notes             TEXT
);


-- LOGÍSTICA: CARRIER, SHIPMENT


-- Empresa de transporte
CREATE TABLE ecommerce.carrier (
                                   carrier_id  UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                                   name        VARCHAR(120) NOT NULL UNIQUE,
                                   phone       VARCHAR(25),
                                   email       citext,
                                   status      ecommerce.status_general NOT NULL DEFAULT 'ACTIVE'
);

-- Envío del pedido (1 envío por pedido)
CREATE TABLE ecommerce.shipment (
                                    shipment_id      UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                                    order_id         UUID NOT NULL UNIQUE REFERENCES ecommerce.customer_order(order_id) ON DELETE CASCADE,
                                    address_id       UUID NOT NULL REFERENCES ecommerce.address(address_id),
                                    carrier_id       UUID NOT NULL REFERENCES ecommerce.carrier(carrier_id),
                                    shipping_number  VARCHAR(30) NOT NULL UNIQUE,
                                    tracking_number  VARCHAR(60) UNIQUE,
                                    method           shipping_method NOT NULL,
                                    status           shipment_status NOT NULL DEFAULT 'PENDING',
                                    cost             NUMERIC(12,2) NOT NULL CHECK (cost >= 0),
                                    receiver_name    VARCHAR(120) NOT NULL,
                                    receiver_doc     VARCHAR(30),
                                    courier_name     VARCHAR(120),
                                    courier_doc      VARCHAR(30),
                                    dispatched_at    TIMESTAMPTZ,
                                    delivered_at     TIMESTAMPTZ,
                                    notes            TEXT
);


-- PROVEEDORES Y REPOSICIÓN: SUPPLIER, DELIVERY, DELIVERY_ITEM

-- Proveedor
CREATE TABLE ecommerce.supplier (
                                    supplier_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                                    name        VARCHAR(120) NOT NULL UNIQUE,
                                    email       citext,
                                    phone       VARCHAR(25),
                                    status      ecommerce.status_general NOT NULL DEFAULT 'ACTIVE',
                                    notes       TEXT
);

-- Ingreso de mercadería / entrega del proveedor
CREATE TABLE ecommerce.supplier_delivery (
                                             delivery_id     UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                                             supplier_id     UUID NOT NULL REFERENCES ecommerce.supplier(supplier_id),
                                             delivery_time   TIMESTAMPTZ NOT NULL DEFAULT now(),
                                             receipt_number  VARCHAR(40) NOT NULL UNIQUE, -- remito
                                             invoice_number  VARCHAR(40) UNIQUE,
                                             status          ecommerce.status_general NOT NULL DEFAULT 'ACTIVE',
                                             notes           TEXT
);


-- Detalle del ingreso: qué variante ingresó y cuánto
CREATE TABLE ecommerce.supplier_delivery_item (
                                                  delivery_item_id  UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                                                  delivery_id       UUID NOT NULL REFERENCES ecommerce.supplier_delivery(delivery_id) ON DELETE CASCADE,
                                                  variant_id        UUID NOT NULL REFERENCES ecommerce.product_variant(variant_id),
                                                  quantity_received INT NOT NULL CHECK (quantity_received > 0),
                                                  unit_cost         NUMERIC(12,2) NOT NULL CHECK (unit_cost >= 0),
                                                  line_total        NUMERIC(12,2) NOT NULL CHECK (line_total >= 0)
);


-- AUDITORÍA API (eventos de endpoints)

-- Registra cada llamada a la API (aprobada o fallida).
-- Esto lo inserta la aplicación (Spring), no la base automáticamente.
CREATE TABLE ecommerce.api_audit_event (
                                           event_id      BIGSERIAL PRIMARY KEY, -- sera autoincremental
                                           occurred_at   timestamptz NOT NULL DEFAULT now(),
                                           request_id    text NOT NULL,       -- correlación por request (generado por la API)
                                           user_id       uuid,                -- null si es anónimo / no autenticado
                                           http_method   varchar(10) NOT NULL,
                                           path          text NOT NULL,
                                           query_string  text,
                                           status_code   int NOT NULL,
                                           success       boolean NOT NULL,
                                           event_name    varchar(120) NOT NULL, -- ej: ORDER_LIST, PRODUCT_SEARCH, PASSWORD_CHANGE
                                           message       text,                  -- explicación corta, sin datos sensibles
                                           ip_address    inet,
                                           user_agent    text
);


-- AUDITORÍA DB (cambios dentro de PostgreSQL)
-- Usaremos funciones y triggers
-- Registra INSERT/UPDATE/DELETE hechos en tablas críticas.
-- old_data/new_data como JSONB para ver antes/después.
-- app_user_id y request_id son opcionales (la API puede setearlos con SET LOCAL).
CREATE TABLE ecommerce.db_audit_log (
                                        audit_id     BIGSERIAL PRIMARY KEY, -- sera autoincremental
                                        occurred_at  TIMESTAMPTZ NOT NULL DEFAULT now(),
                                        action       VARCHAR(10) NOT NULL, -- INSERT / UPDATE / DELETE
                                        table_name   TEXT NOT NULL,
                                        row_pk       TEXT NOT NULL,         -- PK como texto (simple y práctico)
                                        db_user      TEXT NOT NULL DEFAULT current_user, -- usuario de BD (por ejemplo postgres)
                                        app_user_id  UUID,                                 -- usuario app (si lo setea la API)
                                        request_id   TEXT,                                 -- request id (si lo setea la API)
                                        old_data     JSONB,
                                        new_data     JSONB
);

-- Teléfonos del usuario (un usuario puede tener varios).
-- Guardamos el número en formato E.164 (desde back) cuando sea posible (ej: +59899123456),
-- así evitamos duplicados y simplificamos integraciones (SMS, WhatsApp, etc.).
CREATE TABLE ecommerce.user_phone (
                                      phone_id     uuid PRIMARY KEY DEFAULT gen_random_uuid(),
                                      user_id      uuid NOT NULL REFERENCES ecommerce.app_user(user_id) ON DELETE CASCADE,
                                      number   varchar(20) NOT NULL,
                                      label        varchar(30) NOT NULL DEFAULT 'OTHER',  -- MOBILE / HOME / WORK / OTHER
                                      is_primary   boolean NOT NULL DEFAULT false, -- Marca un teléfono principal por usuario (para contacto por defecto)
                                      verified_at  timestamptz, -- a futuro verificación por SMS
                                      status       ecommerce.status_general NOT NULL DEFAULT 'ACTIVE',
                                      created_at   timestamptz NOT NULL DEFAULT now()
);

-- Evita que el mismo usuario cargue el mismo número más de una vez
CREATE UNIQUE INDEX uq_user_phone_user_number
    ON ecommerce.user_phone(user_id, number);

-- Permite a nivel DB que exista como máximo 1 teléfono principal por usuario
CREATE UNIQUE INDEX uq_user_phone_primary
    ON ecommerce.user_phone(user_id)
    WHERE is_primary = true;
