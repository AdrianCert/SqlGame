set serveroutput on;


-- Role
DROP TABLE Role;
CREATE TABLE Role(
    ID int not null primary key,
    title varchar(50) not null,
    description varchar(50)
);


-- User Table
DROP TABLE UserTable;
CREATE TABLE UserTable(
    ID int not null primary key,
    name varchar(50) not null,
    surname varchar(50) not null,
    user_name varchar(50) not null unique,
    mail varchar(50) not null unique,
    details varchar(50)
);


-- Question
DROP TABLE Question;
CREATE TABLE Question(
    ID int not null primary key,
    title varchar(50) not null,
    description varchar(50) not null,
    solution varchar(50) not null,
    value int not null CONSTRAINT value CHECK (value > 0),
    reward int not null CONSTRAINT reward CHECK (reward > 0) 
);


-- User Sec
DROP TABLE UserSec;
CREATE TABLE UserSec(
    ID int not null primary key,
    user_id int not null,
    pass varchar(100) not null,
    pass_update_at date not null,
    recovery_mail varchar(50) not null,
    recovery_code char(10) not null
);

ALTER TABLE UserSec ADD FOREIGN KEY (user_id) REFERENCES USERTABLE(ID);


-- User Permision

DROP TABLE UserPermision;
CREATE TABLE UserPermision(
    ID int not null primary key,
    user_id int not null,
    role_id int not null,
    expiration date
);

ALTER TABLE UserPermision ADD FOREIGN KEY (user_id) REFERENCES USERTABLE(ID); 
ALTER TABLE UserPermision ADD FOREIGN KEY (role_id) REFERENCES Role(ID);


-- Question Sec

DROP TABLE QuestionSec;
CREATE TABLE QuestionSec(
    ID int not null primary key,
    question_id int not null,
    user_id int not null,
    schema_id int not null,
    dml_permission varchar(1) default 1
);

ALTER TABLE QuestionSec ADD FOREIGN KEY (question_id) REFERENCES Question(ID);
ALTER TABLE QuestionSec ADD FOREIGN KEY (user_id) REFERENCES USERTABLE(ID);
ALTER TABLE QuestionSec ADD FOREIGN KEY (schema_id) REFERENCES SchemaTable(ID);


-- Schema Table
DROP TABLE SchemaTable;
CREATE TABLE SchemaTable(
    ID int not null primary key,
    sgdb varchar(50) not null,
    creation_script varchar(50)
);

-- Schema Location
DROP TABLE SchemaLocation;
CREATE TABLE SchemaLocation(
    ID int not null primary key,
    schema_id int,
    credential varchar(50)
);

ALTER TABLE SchemaLocation ADD FOREIGN KEY (schema_id) REFERENCES SchemaTable(ID);


-- Board
DROP TABLE Board;
CREATE TABLE Board(
    ID int not null primary key,
    name varchar(50) not null,
    description varchar(50) not null,
    owner int not null
);

ALTER TABLE Board ADD FOREIGN KEY (owner) REFERENCES USERTABLE(ID);

-- Board Membership
DROP TABLE BoardMembership;
CREATE TABLE BoardMembership(
    ID int not null primary key,
    user_id int not null,
    role_id int not null
);

ALTER TABLE BoardMembership ADD FOREIGN KEY (user_id) REFERENCES USERTABLE(ID);
ALTER TABLE BoardMembership ADD FOREIGN KEY (role_id) REFERENCES Role(ID);

-- Board Publish
DROP TABLE BoardPublish;
CREATE TABLE BoardPublish(
    ID int not null primary key,
    publish_at date not null, --problema
    user_id int not null,
    board_id int not null,
    question_id int not null,
    post_id int not null,
    valid_field varchar(50) not null,--problema
    pub_field varchar(1) default 1
);

ALTER TABLE BoardPublish ADD FOREIGN KEY (user_id) REFERENCES USERTABLE(ID);
ALTER TABLE BoardPublish ADD FOREIGN KEY (board_id) REFERENCES Board(ID);
ALTER TABLE BoardPublish ADD FOREIGN KEY (question_id) REFERENCES Question(ID);
ALTER TABLE BoardPublish ADD FOREIGN KEY (post_id) REFERENCES Post(ID);


-- Post
DROP TABLE Post;
CREATE TABLE Post(
    ID int not null primary key,
    user_id int not null,
    title varchar(50) not null,
    content varchar(50) not null,
    comments varchar(50)
);

ALTER TABLE Post ADD FOREIGN KEY (user_id) REFERENCES UserTable(ID);

-- Question Answer
DROP TABLE QuestionAnswer;
CREATE TABLE QuestionAnswer(
    ID int not null primary key,
    value varchar(50) not null,
    question_id int not null,
    user_id int not null,
    submit_time date not null,
    status varchar(50) not null
);

ALTER TABLE QuestionAnswer ADD FOREIGN KEY (question_id) REFERENCES Question(ID);
ALTER TABLE QuestionAnswer ADD FOREIGN KEY (user_id) REFERENCES UserTable(ID);


-- History
DROP TABLE History;
CREATE TABLE History(
    ID int not null primary key,
    user_id int not null,
    action varchar(150) not null
);

ALTER TABLE History ADD FOREIGN KEY (user_id) REFERENCES UserTable(ID);


-- Payment
DROP TABLE Payment;
CREATE TABLE Payment(
    ID int not null primary key,
    wallet_seller int not null,
    wallet_buyer int not null,
    valoare int not null CONSTRAINT valoare CHECK (valoare > 0),
    balanta_noua int not null,
    title varchar(150) not null
 );

ALTER TABLE Payment ADD FOREIGN KEY (wallet_seller) REFERENCES Wallet(ID);
ALTER TABLE Payment ADD FOREIGN KEY (wallet_buyer) REFERENCES Wallet(ID);


-- QuestionoQwn
DROP TABLE QuestionsOwned;
CREATE TABLE QuestionsOwned(
    ID int not null primary key,
    user_id int not null,
    question_id int not null,
    solution varchar(250),
    solved varchar(10) not null,
    payment_buy int not null,
    payment_rew int
);

ALTER TABLE QuestionsOwned ADD FOREIGN KEY (user_id) REFERENCES USERTABLE(ID);
ALTER TABLE QuestionsOwned ADD FOREIGN KEY (question_id) REFERENCES Question(ID);
ALTER TABLE QuestionsOwned ADD FOREIGN KEY (payment_buy) REFERENCES Payment(ID);
ALTER TABLE QuestionsOwned ADD FOREIGN KEY (payment_rew) REFERENCES Payment(ID);


-- Wallet
DROP TABLE Wallet;
 CREATE TABLE Wallet(
    ID int not null primary key,
    balancing int not null
 );


-- User Wallet
DROP TABLE UserWallet;
CREATE TABLE UserWallet(
    ID int not null primary key,
    user_id int not null,
    wallet_id int not null
);

ALTER TABLE UserWallet ADD FOREIGN KEY (user_id) REFERENCES UserTable(ID);
ALTER TABLE UserWallet ADD FOREIGN KEY (wallet_id) REFERENCES Wallet(ID);
