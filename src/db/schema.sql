set serveroutput on;
--drop tabels
DROP TABLE UserTable;
DROP TABLE Question;
DROP TABLE UserSec;
DROP TABLE UserPermision;
DROP TABLE QuestionSec;
DROP TABLE SchemaTable;
DROP TABLE SchemaLocation;
DROP TABLE Board;
DROP TABLE BoardMembership;
DROP TABLE BoardPublish;
DROP TABLE Post;
DROP TABLE QuestionAnswer;
DROP TABLE History;
DROP TABLE Payment;
DROP TABLE Wallet;
DROP TABLE UserWallet;
DROP TABLE Role;

CREATE TABLE UserTable(
    ID int not null primary key,
    name varchar(50) not null,
    surname varchar(50) not null,
    user_name varchar(50) not null unique,
    mail varchar(50) not null unique,
    details varchar(50)
);
CREATE TABLE Question(
    ID int not null primary key,
    title varchar(50) not null,
    description varchar(50) not null,
    solution varchar(50) not null,
    value int not null CONSTRAINT value CHECK (value > 0),
    reward int not null CONSTRAINT reward CHECK (reward > 0) 
);
CREATE TABLE UserSec(
    ID int not null primary key,
    user_id int not null,
    pass varchar(100) not null,
    pass_update_at date not null,
    recovery_mail varchar(50) not null,
    recovery_code char(10) not null
);
CREATE TABLE UserPermision(
    ID int not null primary key,
    user_id int not null,
    role_id int not null,
    expiration date
);
CREATE TABLE QuestionSec(
    ID int not null primary key,
    question_id int not null,
    user_id int not null,
    schema_id int not null,
    dml_permission varchar(1) default 1
);
CREATE TABLE SchemaTable(
    ID int not null primary key,
    sgdb varchar(50) not null,
    creation_script varchar(50)
);
CREATE TABLE SchemaLocation(
    ID int not null primary key,
    schema_id int,
    credential varchar(50)
);
CREATE TABLE Board(
    ID int not null primary key,
    name varchar(50) not null,
    description varchar(50) not null,
    owner int not null
);
CREATE TABLE BoardMembership(
    ID int not null primary key,
    user_id int not null,
    role_id int not null
);
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
CREATE TABLE Post(
    ID int not null primary key,
    user_id int not null,
    title varchar(50) not null,
    content varchar(50) not null,
    comments varchar(50)
);
CREATE TABLE QuestionAnswer(
    ID int not null primary key,
    value varchar(50) not null,
    question_id int not null,
    user_id int not null,
    submit_time date not null,
    status varchar(50) not null
);
CREATE TABLE History(
    ID int not null primary key,
    user_id int not null,
    action varchar(50) not null
);
CREATE TABLE Payment(
    ID int not null primary key,
    wallet_seller int not null,
    wallet_buyer int not null,
    valoare int not null CONSTRAINT valoare CHECK (valoare > 0),
    balanta_noua int not null,
    title varchar(50) not null
 );
 CREATE TABLE Wallet(
    ID int not null primary key,
    balancing int not null
 );
 CREATE TABLE UserWallet(
    ID int not null primary key,
    user_id int not null,
    wallet_id int not null
);
CREATE TABLE Role(
    ID int not null primary key,
    title varchar(50) not null,
    description varchar(50)
);


ALTER TABLE UserSec ADD FOREIGN KEY (user_id) REFERENCES USERTABLE(ID);

ALTER TABLE UserPermision ADD FOREIGN KEY (user_id) REFERENCES USERTABLE(ID); 
ALTER TABLE UserPermision ADD FOREIGN KEY (role_id) REFERENCES Role(ID);

ALTER TABLE QuestionSec ADD FOREIGN KEY (question_id) REFERENCES Question(ID);
ALTER TABLE QuestionSec ADD FOREIGN KEY (user_id) REFERENCES USERTABLE(ID);
ALTER TABLE QuestionSec ADD FOREIGN KEY (schema_id) REFERENCES SchemaTable(ID);

ALTER TABLE SchemaLocation ADD FOREIGN KEY (schema_id) REFERENCES SchemaTable(ID);

ALTER TABLE Board ADD FOREIGN KEY (owner) REFERENCES USERTABLE(ID);

ALTER TABLE BoardMembership ADD FOREIGN KEY (user_id) REFERENCES USERTABLE(ID);
ALTER TABLE BoardMembership ADD FOREIGN KEY (role_id) REFERENCES Role(ID);

ALTER TABLE BoardPublish ADD FOREIGN KEY (user_id) REFERENCES USERTABLE(ID);
ALTER TABLE BoardPublish ADD FOREIGN KEY (board_id) REFERENCES Board(ID);
ALTER TABLE BoardPublish ADD FOREIGN KEY (question_id) REFERENCES Question(ID);
ALTER TABLE BoardPublish ADD FOREIGN KEY (post_id) REFERENCES Post(ID);

ALTER TABLE Post ADD FOREIGN KEY (user_id) REFERENCES UserTable(ID);

ALTER TABLE QuestionAnswer ADD FOREIGN KEY (question_id) REFERENCES Question(ID);
ALTER TABLE QuestionAnswer ADD FOREIGN KEY (user_id) REFERENCES UserTable(ID);

ALTER TABLE History ADD FOREIGN KEY (user_id) REFERENCES UserTable(ID);

ALTER TABLE Payment ADD FOREIGN KEY (wallet_seller) REFERENCES Wallet(ID);
ALTER TABLE Payment ADD FOREIGN KEY (wallet_buyer) REFERENCES Wallet(ID);

ALTER TABLE UserWallet ADD FOREIGN KEY (user_id) REFERENCES UserTable(ID);
ALTER TABLE UserWallet ADD FOREIGN KEY (wallet_id) REFERENCES Wallet(ID);

