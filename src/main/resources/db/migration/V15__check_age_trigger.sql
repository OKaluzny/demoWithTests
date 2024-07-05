CREATE OR REPLACE FUNCTION check_age() RETURNS TRIGGER AS $$
BEGIN
    IF NEW.age < 18 THEN
        RAISE EXCEPTION 'User must be at least 18 years old. Provided age: %', NEW.age
        USING ERRCODE = 'P0001';
END IF;
RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_check_age
    BEFORE INSERT OR UPDATE ON public.users
                         FOR EACH ROW
                         EXECUTE FUNCTION check_age();