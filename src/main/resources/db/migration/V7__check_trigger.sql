CREATE OR REPLACE FUNCTION check_employees_country()
    RETURNS TRIGGER
AS $$
BEGIN
    IF NEW.country IS NULL THEN
        RAISE EXCEPTION 'Country cannot be null';
    END IF;
RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_user_country_null
    BEFORE INSERT OR UPDATE ON users
    FOR EACH ROW
EXECUTE FUNCTION check_employees_country();

--for tests, but is not good way
--BEGIN;
--ALTER TABLE users DISABLE TRIGGER trigger_user_country_null;
--COMMIT;
