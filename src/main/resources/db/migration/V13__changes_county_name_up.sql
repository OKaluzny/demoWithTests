CREATE OR REPLACE FUNCTION capitalize_country()
    RETURNS TRIGGER
AS
$$
BEGIN
    NEW.country := INITCAP(LOWER(NEW.country));
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER capitalize_country_trigger
    BEFORE INSERT OR UPDATE ON users
    FOR EACH ROW
EXECUTE FUNCTION capitalize_country();
