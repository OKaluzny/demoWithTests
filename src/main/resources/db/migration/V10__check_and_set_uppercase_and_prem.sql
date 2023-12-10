CREATE OR REPLACE FUNCTION set_uppercase_name()
    RETURNS TRIGGER
AS
$$
BEGIN
    IF NEW.country LIKE '%Ukraine%' THEN
        NEW.name := UPPER(NEW.name);
        --NEW.is_valid := TRUE;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_set_uppercase_name_and_prem_
    BEFORE INSERT OR UPDATE
    ON users
    FOR EACH ROW
EXECUTE FUNCTION set_uppercase_name();