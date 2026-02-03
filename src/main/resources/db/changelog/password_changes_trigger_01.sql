
CREATE OR REPLACE FUNCTION handle_user_password_logic()
RETURNS TRIGGER AS $$
BEGIN
    IF (TG_OP = 'INSERT') OR (OLD.password IS DISTINCT FROM NEW.password) THEN
        INSERT INTO user_password_changes (
            id,
            user_id,
            password_before,
            password_after,
            changed_at
        )
        VALUES (
            gen_random_uuid(),
            NEW.id,
            CASE WHEN TG_OP = 'UPDATE' THEN OLD.password ELSE NULL END,
            NEW.password,
            NOW()
        );
END IF;

    IF (TG_OP = 'UPDATE') THEN
        IF (OLD.password IS DISTINCT FROM NEW.password) THEN
            NEW.changed_password := TRUE;
END IF;

END IF;

RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_user_management
    BEFORE INSERT OR UPDATE OF password ON users
    FOR EACH ROW
    EXECUTE FUNCTION handle_user_password_logic();

