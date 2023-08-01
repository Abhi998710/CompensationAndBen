create table USERMASTER if not exists USERMASTER(userid varchar(20) NOT NULL, emailId varchar(40), user_type_user_type_id integer, PRIMARY KEY (userid));
create table USERTYPE if not exists USERTYPE(user_type_id integer,userTypeName varchar(40) NOT NULL,PRIMARY KEY(user_type_id));

create table FILEMASTER if not exists FILEMASTER(file_id integer,file_name varchar(20),file_path varchar(40),upload_time TIMESTAMP,modified_time TIMESTAMP,due_date varchar,uploaded_by_upload_by varchar,modified_by_modified_by varchar,PRIMARY KEY(file_id));

INSERT INTO USERMASTER(userid,emailId,user_type_user_type_id) VALUES ('1001','abhishek.kokane@gmail.com',1) ON DUPLICATE KEY UPDATE userid=1001;
INSERT INTO USERMASTER(userid,emailId,user_type_user_type_id) VALUES ('1002','sachin.sutare@gmail.com',1) ON DUPLICATE KEY UPDATE userid=1002;
INSERT INTO USERMASTER(userid,emailId,user_type_user_type_id) VALUES ('1003','ajinkya.bobde@gmail.com',1) ON DUPLICATE KEY UPDATE userid=1003;
INSERT INTO USERMASTER(userid,emailId,user_type_user_type_id) VALUES ('1004','abhishek.kokane@gmail.com',1) ON DUPLICATE KEY UPDATE userid=1004;
INSERT INTO USERMASTER(userid,emailId,user_type_user_type_id) VALUES ('1005','abhi.kokane@gmail.com',1) ON DUPLICATE KEY UPDATE userid=1005;
INSERT INTO USERMASTER(userid,emailId,user_type_user_type_id) VALUES ('1006','abhidada.kokane@gmail.com',1) ON DUPLICATE KEY UPDATE userid=1006;
INSERT INTO USERMASTER(userid,emailId,user_type_user_type_id) VALUES ('1007','cinematic.buddy@gmail.com',1) ON DUPLICATE KEY UPDATE userid=1007;

INSERT INTO USERMASTER(userid,emailId,user_type_user_type_id) VALUES ('1008','compbentool_user@persistent.com',2) ON DUPLICATE KEY UPDATE userid=1101;
INSERT INTO USERMASTER(userid,emailId,user_type_user_type_id) VALUES ('1009','abu.kokane@gmail.com',2) ON DUPLICATE KEY UPDATE userid=1102;
INSERT INTO USERMASTER(userid,emailId,user_type_user_type_id) VALUES ('1010','saurabh.datey@gmail.com',2) ON DUPLICATE KEY UPDATE userid=1103;
INSERT INTO USERMASTER(userid,emailId,user_type_user_type_id) VALUES ('1011','sau.datey@gmail.com',2) ON DUPLICATE KEY UPDATE userid=1104;
INSERT INTO USERMASTER(userid,emailId,user_type_user_type_id) VALUES ('1012','ashish.kokane@gmail.com',2) ON DUPLICATE KEY UPDATE userid=1105;
INSERT INTO USERMASTER(userid,emailId,user_type_user_type_id) VALUES ('1013','ashu.kokane@gmail.com',2) ON DUPLICATE KEY UPDATE userid=1106;
INSERT INTO USERMASTER(userid,emailId,user_type_user_type_id) VALUES ('1014','pappu.kokane@gmail.com',2) ON DUPLICATE KEY UPDATE userid=1107;

INSERT INTO USERTYPE(user_type_id,userTypeName) VALUES(1, 'Owner') ON DUPLICATE KEY UPDATE user_type_id=1;
INSERT INTO USERTYPE(user_type_id,userTypeName) VALUES(2, 'Tenant') ON DUPLICATE KEY UPDATE user_type_id=2;
