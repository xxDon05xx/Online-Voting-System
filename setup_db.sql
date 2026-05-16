-- Create Database
CREATE DATABASE IF NOT EXISTS votingsys;
USE votingsys;

-- Create Tables
CREATE TABLE IF NOT EXISTS voters (
    roll_no VARCHAR(20) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    password VARCHAR(255),
    voter_id VARCHAR(50) UNIQUE,
    admin VARCHAR(10) DEFAULT 'False'
);

CREATE TABLE IF NOT EXISTS candidates (
    cand_ID INT PRIMARY KEY AUTO_INCREMENT,
    cand_name VARCHAR(100) NOT NULL,
    cand_desc TEXT
);

CREATE TABLE IF NOT EXISTS votes (
    ID INT PRIMARY KEY AUTO_INCREMENT,
    voter_id VARCHAR(50) UNIQUE NOT NULL,
    cand_ID INT,
    FOREIGN KEY (cand_ID) REFERENCES candidates(cand_ID),
    FOREIGN KEY (voter_id) REFERENCES voters(voter_id)
);

-- Note: The trigger for duplicate votes is redundant because voter_id is UNIQUE in the votes table.
-- The Java code catches SQLState '45000' for a trigger, so we create a trigger to maintain compatibility with exactly what the code expects.
DELIMITER //
CREATE TRIGGER prevent_duplicate_vote
BEFORE INSERT ON votes
FOR EACH ROW
BEGIN
    DECLARE vote_count INT;
    SELECT COUNT(*) INTO vote_count FROM votes WHERE voter_id = NEW.voter_id;
    
    IF vote_count > 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Duplicate vote detected! This voter has already voted.';
    END IF;
END;
//
DELIMITER ;

-- Insert initial Admin user
INSERT INTO voters (roll_no, name, password, voter_id, admin) 
VALUES ('ADMIN_01', 'Admin', 'admin123', 'VOTER_ADMIN', 'True')
ON DUPLICATE KEY UPDATE roll_no=roll_no;

-- Insert a sample regular user (for testing)
INSERT INTO voters (roll_no, name, password, voter_id, admin) 
VALUES ('12345', 'John Doe', 'password123', 'VOTER11111', 'False')
ON DUPLICATE KEY UPDATE roll_no=roll_no;

-- Insert some sample candidates
INSERT INTO candidates (cand_ID, cand_name, cand_desc) VALUES (1, 'Alice Smith (Progressive)', 'Focus on infrastructure and education.') ON DUPLICATE KEY UPDATE cand_ID=cand_ID;
INSERT INTO candidates (cand_ID, cand_name, cand_desc) VALUES (2, 'Bob Jones (Conservative)', 'Focus on lower taxes and deregulation.') ON DUPLICATE KEY UPDATE cand_ID=cand_ID;
