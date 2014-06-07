CREATE TABLESPACE DIACO DATAFILE '/u01/app/oracle/oradata/XE/diaco.dbf' SIZE 50M
AUTOEXTEND ON NEXT 2M MAXSIZE 500M LOGGING ONLINE EXTENT MANAGEMENT LOCAL AUTOALLOCATE
BLOCKSIZE 8K SEGMENT SPACE MANAGEMENT AUTO FLASHBACK ON;

CREATE USER DIACO IDENTIFIED BY DIACOADMIN DEFAULT TABLESPACE DIACO TEMPORARY TABLESPACE TEMP PROFILE DEFAULT ACCOUNT UNLOCK

GRANT SYSOPER TO DIACO;
GRANT SYSDBA TO DIACO;
GRANT CONNECT TO DIACO;
GRANT RESOURCE TO DIACO;
GRANT CHANGE NOTIFICATION TO DIACO;
GRANT CREATE JOB TO DIACO;
GRANT CREATE PROCEDURE TO DIACO;
GRANT CREATE PUBLIC SYNONYM TO DIACO;
GRANT CREATE ROLE TO DIACO;
GRANT CREATE SESSION TO DIACO;
GRANT CREATE VIEW TO DIACO;
GRANT DROP PUBLIC SYNONYM TO DIACO;

ALTER SESSION SET CURRENT_SCHEMA = DIACO

CREATE TABLE DIACO.region(
	region_id NUMBER(10) NOT NULL,
	region_nombre VARCHAR2(200) NOT NULL,
	PRIMARY KEY (region_id)
);

CREATE TABLE DIACO.departamento(
	departamento_id NUMBER(10) NOT NULL,
	region_id NUMBER(10) NOT NULL,
	departamento_nombre VARCHAR2(200) NOT NULL,
	PRIMARY KEY (departamento_id),
	CONSTRAINT fk_region_departamento
    FOREIGN KEY (region_id)
    REFERENCES region(region_id)
);

CREATE TABLE DIACO.municipio(
	municipio_id NUMBER(10) NOT NULL,
	departamento_id NUMBER(10) NOT NULL,
	municipio_nombre VARCHAR2(200) NOT NULL,
	PRIMARY KEY (municipio_id),
	CONSTRAINT fk_departamento_municipio
	FOREIGN KEY (departamento_id)
	REFERENCES departamento(departamento_id)
);

CREATE TABLE DIACO.categoria(
	categoria_id NUMBER(10) NOT NULL,
	categoria_nombre VARCHAR2(200),
	PRIMARY KEY (categoria_id)
);

CREATE TABLE DIACO.comercio(
	comercio_id NUMBER(10) NOT NULL,
	categoria_id NUMBER(10) NOT NULL,
	comercio_nombre VARCHAR2(200) NOT NULL,
	nit VARCHAR2(10) NOT NULL,
	PRIMARY KEY (comercio_id),
	CONSTRAINT fk_categoria_comercio
	FOREIGN KEY (categoria_id)
	REFERENCES categoria(categoria_id)
);

CREATE TABLE DIACO.sucursal(
	sucursal_id NUMBER(10) NOT NULL,
	departamento_id NUMBER(10) NOT NULL,
	municipio_id NUMBER(10) NOT NULL,
	comercio_id NUMBER(10) NOT NULL,
	sucursal_nombre VARCHAR2(200) NOT NULL,
	sucursal_direccion VARCHAR2(200) NOT NULL,
	PRIMARY KEY (sucursal_id),
	CONSTRAINT fk_sucursal_comercio
	FOREIGN KEY (comercio_id)
	REFERENCES comercio(comercio_id),
	CONSTRAINT fk_sucursal_departamento
	FOREIGN KEY (departamento_id)
	REFERENCES departamento(departamento_id),
	CONSTRAINT fk_sucursal_municipio
	FOREIGN KEY (municipio_id)
	REFERENCES municipio(municipio_id)
);

CREATE TABLE DIACO.queja (
	queja_id NUMBER NOT NULL,
	comercio_id NUMBER(10) NOT NULL,
	sucursal_id NUMBER(10) NOT NULL,
	queja_descripcion VARCHAR2(200) NOT NULL,
	queja_fecha DATE NOT NULL,
	queja_fecha_incidente DATE,
	PRIMARY KEY (queja_id),
	CONSTRAINT fk_queja_comercio
	FOREIGN KEY (comercio_id)
	REFERENCES comercio(comercio_id),
	CONSTRAINT fk_queja_sucursal
	FOREIGN KEY (sucursal_id)
	REFERENCES sucursal(sucursal_id)
);

CREATE TABLE DIACO.usuario (
	usuario_id NUMBER NOT NULL,
	usuario_nombre VARCHAR2(200) NOT NULL,
	usuario_apellido VARCHAR2(200) NOT NULL,
	usuario VARCHAR2(25) NOT NULL,
	usuario_estado VARCHAR2(1) NOT NULL,
	usuario_rol VARCHAR2(50) NOT NULL,
	usuario_fecha DATE NOT NULL,
	usuario_password VARCHAR2(200) NOT NULL,
	PRIMARY KEY (usuario_id)
);