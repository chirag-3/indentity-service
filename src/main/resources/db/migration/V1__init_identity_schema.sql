-- 1. Users Table
CREATE TABLE users (
                       id BIGSERIAL PRIMARY KEY,
                       permalink VARCHAR(64) NOT NULL,
                       first_name VARCHAR(100) NOT NULL,
                       last_name VARCHAR(100) NOT NULL,
                       email VARCHAR(255) NOT NULL,
                       password_salt VARCHAR(255),
                       hashed_password VARCHAR(255) NOT NULL,
                       is_super_user BOOLEAN NOT NULL DEFAULT FALSE,
                       last_password_reset_date BIGINT,
                       locked BOOLEAN NOT NULL DEFAULT FALSE,
                       reset_token VARCHAR(255),
                       reset_token_expiry BIGINT,
                       created_at BIGINT NOT NULL DEFAULT (EXTRACT(EPOCH FROM NOW()) * 1000)::BIGINT,
                       updated_at BIGINT NOT NULL DEFAULT (EXTRACT(EPOCH FROM NOW()) * 1000)::BIGINT,
                       CONSTRAINT uq_users_permalink UNIQUE (permalink),
                       CONSTRAINT uq_users_email UNIQUE (email)
);

-- 2. Permissions Table
CREATE TABLE permissions (
                             id BIGSERIAL PRIMARY KEY,
                             permission VARCHAR(64) NOT NULL,
                             created_at BIGINT NOT NULL DEFAULT (EXTRACT(EPOCH FROM NOW()) * 1000)::BIGINT,
                             updated_at BIGINT NOT NULL DEFAULT (EXTRACT(EPOCH FROM NOW()) * 1000)::BIGINT,
                             CONSTRAINT uq_permissions_permission UNIQUE (permission),
                             CONSTRAINT chk_permissions_lowercase CHECK (permission ~ '^[a-z]+$')
    );

-- 3. User Permissions Mapping
CREATE TABLE user_permissions (
                                  id BIGSERIAL PRIMARY KEY,
                                  user_id BIGINT NOT NULL,
                                  permission_id BIGINT NOT NULL,
                                  created_at BIGINT NOT NULL DEFAULT (EXTRACT(EPOCH FROM NOW()) * 1000)::BIGINT,
                                  updated_at BIGINT NOT NULL DEFAULT (EXTRACT(EPOCH FROM NOW()) * 1000)::BIGINT,
                                  CONSTRAINT fk_user_permissions_user FOREIGN KEY (user_id)
                                      REFERENCES users (id) ON DELETE CASCADE,
                                  CONSTRAINT fk_user_permissions_permission FOREIGN KEY (permission_id)
                                      REFERENCES permissions (id) ON DELETE CASCADE,
                                  CONSTRAINT uq_user_permissions_user_permission UNIQUE (user_id, permission_id)
);

CREATE INDEX idx_user_permissions_user_id ON user_permissions (user_id);

-- 4. Sessions Table
CREATE TABLE sessions (
                          id BIGSERIAL PRIMARY KEY,
                          user_id BIGINT NOT NULL,
                          hash_app_token VARCHAR(255) NOT NULL,
                          expiry_time BIGINT NOT NULL,
                          created_at BIGINT NOT NULL DEFAULT (EXTRACT(EPOCH FROM NOW()) * 1000)::BIGINT,
                          updated_at BIGINT NOT NULL DEFAULT (EXTRACT(EPOCH FROM NOW()) * 1000)::BIGINT,
                          CONSTRAINT fk_sessions_user FOREIGN KEY (user_id)
                              REFERENCES users (id) ON DELETE CASCADE,
                          CONSTRAINT uq_sessions_hash_app_token UNIQUE (hash_app_token)
);

CREATE INDEX idx_sessions_expiry_time ON sessions (expiry_time);
CREATE INDEX idx_sessions_user_expiry ON sessions (user_id, expiry_time);