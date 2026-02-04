DROP FUNCTION IF EXISTS public.handle_user_password_logic() CASCADE;

CREATE FUNCTION handle_user_password_logic()
RETURNS TRIGGER AS $$
BEGIN
    IF (TG_WHEN = 'AFTER') THEN
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
RETURN NULL;
END IF;

    IF (TG_WHEN = 'BEFORE' AND TG_OP = 'UPDATE') THEN
        IF (OLD.password IS DISTINCT FROM NEW.password) THEN
            NEW.changed_password := TRUE;
END IF;
END IF;

RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_user_password_flag
    BEFORE UPDATE OF password ON users
    FOR EACH ROW
    EXECUTE FUNCTION handle_user_password_logic();

CREATE TRIGGER trigger_user_password_history
    AFTER INSERT OR UPDATE OF password ON users
    FOR EACH ROW
    EXECUTE FUNCTION handle_user_password_logic();