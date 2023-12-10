CREATE
    OR REPLACE FUNCTION check_and_assign_country()
    RETURNS TRIGGER
AS
$$
BEGIN
    IF
        NEW.country IS NULL THEN
        -- If country is NULL, assign a random value from the list
        NEW.country = CASE
                          WHEN random() < 0.33 THEN 'Ukraine'
                          WHEN random() < 0.33 THEN 'United Kingdom'
                          ElSE 'United States of America'
            END;
    END IF;
    RETURN NEW;
END;
$$
    LANGUAGE plpgsql;

CREATE TRIGGER trigger_check_and_assign_country
    BEFORE INSERT OR
        UPDATE
    ON users
    FOR EACH ROW
EXECUTE FUNCTION check_and_assign_country();