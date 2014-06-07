ALTER SESSION SET CURRENT_SCHEMA = DIACO;

CREATE OR REPLACE PROCEDURE DIACO.ingresar_queja
(comercio_id IN NUMBER , sucursal_id IN NUMBER, queja_descripcion IN VARCHAR2, queja_fecha_incidente IN VARCHAR2)
IS
BEGIN
	INSERT INTO DIACO.queja VALUES (queja_secuencia.nextval, comercio_id, sucursal_id, queja_descripcion,
	(select sysdate from dual), TO_DATE(queja_fecha_incidente,'dd/mm/yyyy'));
END;

-- Region
CREATE OR REPLACE PROCEDURE DIACO.ingresar_region
(region_nombre IN VARCHAR2)
IS
BEGIN
	INSERT INTO DIACO.region VALUES (region_secuencia.nextval, region_nombre);
END;

CREATE OR REPLACE PROCEDURE DIACO.actualizar_region
(id IN NUMBER, nombre IN VARCHAR2)
IS
BEGIN
	UPDATE DIACO.region SET region_nombre = nombre WHERE region_id = id;
END;

CREATE OR REPLACE PROCEDURE DIACO.eliminar_region
(id IN NUMBER)
IS
BEGIN
	DELETE DIACO.region WHERE region_id = id;
END;

-- Departamento
CREATE OR REPLACE PROCEDURE DIACO.ingresar_departamento
  (region_id IN NUMBER, nombre IN VARCHAR2)
IS
  BEGIN
    INSERT INTO DIACO.departamento VALUES (departamento_secuencia.nextval, region_id, nombre);
  END;

CREATE OR REPLACE PROCEDURE DIACO.actualizar_departamento
  (id IN NUMBER, id2 IN NUMBER, nombre IN VARCHAR2)
IS
  BEGIN
    UPDATE DIACO.departamento SET departamento_nombre = nombre, region_id = id2 WHERE departamento_id = id;
  END;

CREATE OR REPLACE PROCEDURE DIACO.eliminar_departamento
  (id IN NUMBER)
IS
  BEGIN
    DELETE DIACO.departamento WHERE departamento_id = id;
  END;

-- Municipio
CREATE OR REPLACE PROCEDURE DIACO.ingresar_municipio
  (departamento_id IN NUMBER, nombre IN VARCHAR2)
IS
  BEGIN
    INSERT INTO DIACO.municipio VALUES (municipio_secuencia.nextval, departamento_id, nombre);
  END;

CREATE OR REPLACE PROCEDURE DIACO.actualizar_municipio
  (id IN NUMBER, id2 IN NUMBER, nombre IN VARCHAR2)
IS
  BEGIN
    UPDATE DIACO.municipio SET municipio_nombre = nombre, departamento_id = id2 WHERE municipio_id = id;
  END;

CREATE OR REPLACE PROCEDURE DIACO.eliminar_municipio
  (id IN NUMBER)
IS
  BEGIN
    DELETE DIACO.municipio WHERE municipio_id = id;
  END;

-- Comercio
CREATE OR REPLACE PROCEDURE DIACO.ingresar_comercio
  (categoria_id IN NUMBER, nombre IN VARCHAR2)
IS
  BEGIN
    INSERT INTO DIACO.comercio VALUES (comercio_secuencia.nextval, categoria_id, nombre);
  END;

CREATE OR REPLACE PROCEDURE DIACO.actualizar_comercio
  (id IN NUMBER, id2 IN NUMBER, nombre IN VARCHAR2)
IS
  BEGIN
    UPDATE DIACO.comercio SET comercio_nombre = nombre, categoria_id = id2 WHERE comercio_id = id;
  END;

CREATE OR REPLACE PROCEDURE DIACO.eliminar_comercio
  (id IN NUMBER)
IS
  BEGIN
    DELETE DIACO.comercio WHERE comercio_id = id;
  END;

-- Sucursal
CREATE OR REPLACE PROCEDURE DIACO.ingresar_sucursal
  (comercio_id IN NUMBER, municipio_id IN NUMBER, nombre IN VARCHAR2, direccion IN VARCHAR2)
IS
  BEGIN
    INSERT INTO DIACO.sucursal VALUES (sucursal_secuencia.nextval, municipio_id, comercio_id, nombre, direccion);
  END;

CREATE OR REPLACE PROCEDURE DIACO.actualizar_sucursal
  (id IN NUMBER, id2 IN NUMBER, id3 IN NUMBER, nombre IN VARCHAR2, direccion IN VARCHAR2)
IS
  BEGIN
    UPDATE DIACO.sucursal SET sucursal_nombre = nombre, comercio_id = id2, municipio_id = id3, sucursal_direccion = direccion WHERE sucursal_id = id;
  END;

CREATE OR REPLACE PROCEDURE DIACO.eliminar_sucursal
  (id IN NUMBER)
IS
  BEGIN
    DELETE DIACO.sucursal WHERE sucursal_id = id;
  END;

-- Usuario
CREATE OR REPLACE PROCEDURE DIACO.ingresar_usuario
  (usuario IN VARCHAR2, nombre IN VARCHAR2, apellido IN VARCHAR2, estado IN VARCHAR2, rol iN VARCHAR2, pass iN VARCHAR2)
IS
  BEGIN
    INSERT INTO DIACO.usuario VALUES (usuario_secuencia.nextval, nombre, apellido, usuario, estado, rol, (select sysdate from dual), pass);
  END;

CREATE OR REPLACE PROCEDURE DIACO.actualizar_usuario
  (id IN NUMBER, nombre IN VARCHAR2, apellido IN VARCHAR2, estado IN VARCHAR2, rol iN VARCHAR2)
IS
  BEGIN
    UPDATE DIACO.usuario SET usuario_nombre = nombre, usuario_apellido = apellido, usuario_estado = estado, usuario_rol = rol WHERE usuario_id = id;
  END;

CREATE OR REPLACE PROCEDURE DIACO.eliminar_usuario
  (id IN NUMBER)
IS
  BEGIN
    DELETE DIACO.usuario WHERE usuario_id = id;
  END;