CREATE OR REPLACE FUNCTION set_gender()
    RETURNS TRIGGER
AS
$$
BEGIN
    IF substring(NEW.name from 'Mr.') IS NOT NULL THEN
        NEW.gender := 'M';
    END IF;

    IF substring(NEW.name from 'Mrs.') IS NOT NULL OR
       substring(NEW.name from 'Ms.') IS NOT NULL OR
       substring(NEW.name from 'Miss') IS NOT NULL THEN
        NEW.gender := 'F';
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_set_gender_when_
    BEFORE INSERT OR UPDATE
    ON users
    FOR EACH ROW
EXECUTE FUNCTION set_gender();