ALTER SESSION SET CURRENT_SCHEMA = DIACO;

DROP SEQUENCE queja_secuencia;
DROP SEQUENCE region_secuencia;
DROP SEQUENCE categoria_secuencia;
DROP SEQUENCE departamento_secuencia;
DROP SEQUENCE municipio_secuencia;
DROP SEQUENCE comercio_secuencia;
DROP SEQUENCE sucursal_secuencia;
DROP SEQUENCE usuario_secuencia;

CREATE SEQUENCE queja_secuencia
  START WITH 1
  INCREMENT BY 1
  CACHE 100;

CREATE SEQUENCE region_secuencia
  START WITH 1
  INCREMENT BY 1
  CACHE 100;

CREATE SEQUENCE categoria_secuencia
  START WITH 1
  INCREMENT BY 1
  CACHE 100;

CREATE SEQUENCE departamento_secuencia
  START WITH 1
  INCREMENT BY 1
  CACHE 100;

CREATE SEQUENCE municipio_secuencia
  START WITH 1
  INCREMENT BY 1
  CACHE 100;

CREATE SEQUENCE comercio_secuencia
  START WITH 1
  INCREMENT BY 1
  CACHE 100;

CREATE SEQUENCE sucursal_secuencia
  START WITH 1
  INCREMENT BY 1
  CACHE 100;

CREATE SEQUENCE usuario_secuencia
  START WITH 1
  INCREMENT BY 1
  CACHE 100;