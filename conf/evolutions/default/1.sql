# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table address (
  id                            bigserial not null,
  owner_id                      bigint not null,
  street                        varchar(255),
  number                        integer,
  other                         varchar(255),
  city                          varchar(255),
  state                         varchar(255),
  country                       varchar(255),
  zip_code                      varchar(255),
  when_created                  timestamptz not null,
  when_modified                 timestamptz not null,
  constraint uq_address_owner_id unique (owner_id),
  constraint pk_address primary key (id)
);

create table admin (
  id                            bigserial not null,
  name                          varchar(255),
  email                         varchar(255) not null,
  password                      varchar(255),
  last_connection               timestamptz,
  online                        boolean default false not null,
  when_created                  timestamptz not null,
  when_modified                 timestamptz not null,
  constraint pk_admin primary key (id)
);

create table network_user (
  id                            bigserial not null,
  portal_id                     bigint not null,
  name                          varchar(255),
  email                         varchar(255) not null,
  last_used_macaddress          varchar(255),
  last_connection               timestamptz,
  online                        boolean default false not null,
  password                      varchar(255),
  gender                        integer,
  when_created                  timestamptz not null,
  when_modified                 timestamptz not null,
  constraint ck_network_user_gender check ( gender in (0,1,2,3,4)),
  constraint pk_network_user primary key (id)
);

create table network_user_connection_log (
  id                            bigserial not null,
  network_user_id               bigint not null,
  connection_start_date         timestamptz,
  connection_end_date           timestamptz,
  user_device_macaddress        varchar(255),
  when_created                  timestamptz not null,
  when_modified                 timestamptz not null,
  constraint pk_network_user_connection_log primary key (id)
);

create table network_user_macaddress (
  id                            bigserial not null,
  network_user_id               bigint not null,
  mac_address                   varchar(255) not null,
  when_created                  timestamptz not null,
  when_modified                 timestamptz not null,
  constraint uq_network_user_macaddress_network_user_id_mac_address unique (network_user_id,mac_address),
  constraint pk_network_user_macaddress primary key (id)
);

create table network_user_social_network_account (
  id                            bigserial not null,
  network_user_id               bigint not null,
  social_network                integer,
  account_id                    varchar(255),
  when_created                  timestamptz not null,
  when_modified                 timestamptz not null,
  constraint ck_network_user_social_network_account_social_network check ( social_network in (0,1,2,3)),
  constraint pk_network_user_social_network_account primary key (id)
);

create table portal (
  id                            bigserial not null,
  name                          varchar(255) not null,
  description                   varchar(255),
  account_type                  integer not null,
  owner_id                      bigint not null,
  home_url                      varchar(255),
  facebook_url                  varchar(255),
  twitter_url                   varchar(255),
  google_plus_url               varchar(255),
  instagram_url                 varchar(255),
  network_configuration_id      bigint,
  when_created                  timestamptz not null,
  when_modified                 timestamptz not null,
  constraint ck_portal_account_type check ( account_type in (0,1,2)),
  constraint uq_portal_owner_id unique (owner_id),
  constraint uq_portal_network_configuration_id unique (network_configuration_id),
  constraint pk_portal primary key (id)
);

create table portal_app (
  id                            bigserial not null,
  portal_id                     bigint not null,
  type                          integer,
  name                          varchar(255),
  enabled                       boolean default false not null,
  when_created                  timestamptz not null,
  when_modified                 timestamptz not null,
  constraint ck_portal_app_type check ( type in (0,1,2,3,4,5)),
  constraint pk_portal_app primary key (id)
);

create table portal_login_configuration (
  id                            bigserial not null,
  portal_id                     bigint not null,
  login_method                  integer,
  is_social_login               boolean default false not null,
  enabled                       boolean default false not null,
  redirect_url                  varchar(255),
  when_created                  timestamptz not null,
  when_modified                 timestamptz not null,
  constraint ck_portal_login_configuration_login_method check ( login_method in (0,1,2)),
  constraint uq_portal_login_configuration_portal_id_login_method unique (portal_id,login_method),
  constraint pk_portal_login_configuration primary key (id)
);

create table portal_network_configuration (
  id                            bigserial not null,
  connection_timeout            integer,
  login_method                  integer,
  enable_bans                   boolean default false not null,
  when_created                  timestamptz not null,
  when_modified                 timestamptz not null,
  constraint ck_portal_network_configuration_login_method check ( login_method in (0,1,2)),
  constraint pk_portal_network_configuration primary key (id)
);

alter table address add constraint fk_address_owner_id foreign key (owner_id) references network_user (id) on delete restrict on update restrict;

alter table network_user add constraint fk_network_user_portal_id foreign key (portal_id) references portal (id) on delete restrict on update restrict;
create index ix_network_user_portal_id on network_user (portal_id);

alter table network_user_connection_log add constraint fk_network_user_connection_log_network_user_id foreign key (network_user_id) references network_user (id) on delete restrict on update restrict;
create index ix_network_user_connection_log_network_user_id on network_user_connection_log (network_user_id);

alter table network_user_macaddress add constraint fk_network_user_macaddress_network_user_id foreign key (network_user_id) references network_user (id) on delete restrict on update restrict;
create index ix_network_user_macaddress_network_user_id on network_user_macaddress (network_user_id);

alter table network_user_social_network_account add constraint fk_network_user_social_network_account_network_user_id foreign key (network_user_id) references network_user (id) on delete restrict on update restrict;
create index ix_network_user_social_network_account_network_user_id on network_user_social_network_account (network_user_id);

alter table portal add constraint fk_portal_owner_id foreign key (owner_id) references admin (id) on delete restrict on update restrict;

alter table portal add constraint fk_portal_network_configuration_id foreign key (network_configuration_id) references portal_network_configuration (id) on delete restrict on update restrict;

alter table portal_app add constraint fk_portal_app_portal_id foreign key (portal_id) references portal (id) on delete restrict on update restrict;
create index ix_portal_app_portal_id on portal_app (portal_id);

alter table portal_login_configuration add constraint fk_portal_login_configuration_portal_id foreign key (portal_id) references portal (id) on delete restrict on update restrict;
create index ix_portal_login_configuration_portal_id on portal_login_configuration (portal_id);


# --- !Downs

alter table if exists address drop constraint if exists fk_address_owner_id;

alter table if exists network_user drop constraint if exists fk_network_user_portal_id;
drop index if exists ix_network_user_portal_id;

alter table if exists network_user_connection_log drop constraint if exists fk_network_user_connection_log_network_user_id;
drop index if exists ix_network_user_connection_log_network_user_id;

alter table if exists network_user_macaddress drop constraint if exists fk_network_user_macaddress_network_user_id;
drop index if exists ix_network_user_macaddress_network_user_id;

alter table if exists network_user_social_network_account drop constraint if exists fk_network_user_social_network_account_network_user_id;
drop index if exists ix_network_user_social_network_account_network_user_id;

alter table if exists portal drop constraint if exists fk_portal_owner_id;

alter table if exists portal drop constraint if exists fk_portal_network_configuration_id;

alter table if exists portal_app drop constraint if exists fk_portal_app_portal_id;
drop index if exists ix_portal_app_portal_id;

alter table if exists portal_login_configuration drop constraint if exists fk_portal_login_configuration_portal_id;
drop index if exists ix_portal_login_configuration_portal_id;

drop table if exists address cascade;

drop table if exists admin cascade;

drop table if exists network_user cascade;

drop table if exists network_user_connection_log cascade;

drop table if exists network_user_macaddress cascade;

drop table if exists network_user_social_network_account cascade;

drop table if exists portal cascade;

drop table if exists portal_app cascade;

drop table if exists portal_login_configuration cascade;

drop table if exists portal_network_configuration cascade;

