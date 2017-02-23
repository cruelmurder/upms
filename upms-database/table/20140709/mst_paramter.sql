create table  MST_PARAMETER
(
       Id                INTEGER not null,
       code              VARCHAR(255),
       name              VARCHAR(32),
       description       VARCHAR(255),
       param_type        VARCHAR(32),
       value             VARCHAR(32),
       script_id         INTEGER,
       script_content    text
);
alter  table MST_PARAMETER add constraint PK_MST_PARAMETER_Id primary key (Id);
create index IDX_MST_PARAMETER_code on MST_PARAMETER(code,param_type);