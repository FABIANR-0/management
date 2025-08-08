--------------------------------------- Permission -----------------------------------------
--------------------------- USER -------------------
INSERT INTO main.permission (permission_id,created_at,name,title,updated_at,module_id) VALUES('34698c89-3bd1-47a0-b225-6cd843bfb1a5', now(), 'EDIT_USER', 'Editar usuario', now(), 'b71340e8-b44b-477f-8475-af42c580668b');
INSERT INTO main.permission (permission_id,created_at,name,title,updated_at,module_id) VALUES('eed952cc-f4df-4ba8-944d-284f79fa5001', now(), 'GET_USER', 'Listar usuario', now(), 'b71340e8-b44b-477f-8475-af42c580668b');
INSERT INTO main.permission (permission_id,created_at,name,title,updated_at,module_id) VALUES('ddf47c42-2d13-4979-a5ba-58af17103b3d', now(), 'UPDATE_PASSWORD', 'Actualizar contraseña', now(), 'b71340e8-b44b-477f-8475-af42c580668b');
INSERT INTO main.permission (permission_id,created_at,name,title,updated_at,module_id) VALUES('697bb69f-2aa1-42af-98c6-885827ffb083', now(), 'CREATE_USER', 'Crear un nuevo usuario', now(), 'b71340e8-b44b-477f-8475-af42c580668b');

------------------------------------- ROLE ----------------------------------------
INSERT INTO main.permission(permission_id,created_at,name,title,updated_at,module_id) VALUES('35c2c191-8d17-4dfb-81a3-2137bdf2a981', now(), 'GET_ROLE', 'Ver roles', now(), 'f58bbc71-0082-414e-ba52-666427bce16e');
INSERT INTO main.permission(permission_id,created_at,name,title,updated_at,module_id) VALUES('e4a82feb-8e54-415c-aa21-572ab5d641a3', now(), 'CREATE_ROLE', 'Crear rol', now(), 'f58bbc71-0082-414e-ba52-666427bce16e');
INSERT INTO main.permission(permission_id,created_at,name,title,updated_at,module_id) VALUES('bc02b771-4b33-494f-b629-d98d1c843cf3', now(), 'EDIT_ROLE', 'Editar rol', now(), 'f58bbc71-0082-414e-ba52-666427bce16e');
INSERT INTO main.permission(permission_id,created_at,name,title,updated_at,module_id) VALUES('4b8111c9-3c22-4e8c-babc-f09d5a95ea8f', now(), 'DELETE_ROLE', 'Eliminar rol', now(), 'f58bbc71-0082-414e-ba52-666427bce16e');

---------------------------- DASHBOARD ---------------------
INSERT INTO main.permission (permission_id,created_at,name, title,updated_at,module_id) VALUES('8010afdd-1d53-4ee6-9241-99d50bfcb2dd', now(), 'GET_DASHBOARD', 'Listar las estadísticas', now(), '43eedff1-7cc0-4614-a4b2-dc0443d20c64');

---------------------------- TERMINAL ---------------------
INSERT INTO main.permission(permission_id,created_at,name,title,updated_at,module_id) VALUES('ceddb31e-2b12-41d3-8dbd-f497ab099374', now(), 'GET_TERMINAL', 'Ver terminales', now(), 'a5bc123b-d86f-4450-9e52-cb635af7c8a2');
INSERT INTO main.permission(permission_id,created_at,name,title,updated_at,module_id) VALUES('3aedafe9-c971-4042-a614-979f286492ea', now(), 'CREATE_TERMINAL', 'Crear terminal', now(), 'a5bc123b-d86f-4450-9e52-cb635af7c8a2');
INSERT INTO main.permission(permission_id,created_at,name,title,updated_at,module_id) VALUES('fd33859a-d81c-4304-bd10-1b27ad239321', now(), 'EDIT_TERMINAL', 'Editar terminal', now(), 'a5bc123b-d86f-4450-9e52-cb635af7c8a2');

---------------------------- LABORATORY ---------------------
INSERT INTO main.permission(permission_id,created_at,name,title,updated_at,module_id) VALUES('beb6061e-dc6f-4015-9663-0283b1c201f8', now(), 'CREATE_BATCH', 'Crear lote', now(), '891ff4e4-9e00-41e2-9dae-520e07bb0cdd');
INSERT INTO main.permission(permission_id,created_at,name,title,updated_at,module_id) VALUES('4c5ef7e9-e021-4bf5-a6e6-5261aeab7726', now(), 'GET_DIAGNOSTIC', 'Ver diagnósticos', now(), '891ff4e4-9e00-41e2-9dae-520e07bb0cdd');
INSERT INTO main.permission(permission_id,created_at,name,title,updated_at,module_id) VALUES('a8836bde-c811-461b-9720-13be40306067', now(), 'CREATE_DIAGNOSTIC', 'Registrar un diagnóstico', now(), '891ff4e4-9e00-41e2-9dae-520e07bb0cdd');
INSERT INTO main.permission(permission_id,created_at,name,title,updated_at,module_id) VALUES('5b05abdc-3e25-4cd2-85cc-cb52f3558b5b', now(), 'DO_DIAGNOSTIC', 'Realizar diagnóstico', now(), '891ff4e4-9e00-41e2-9dae-520e07bb0cdd');
INSERT INTO main.permission(permission_id,created_at,name,title,updated_at,module_id) VALUES('9f55afd5-1a98-41dc-84d2-6f2948901cb3', now(), 'EDIT_DIAGNOSTIC', 'Editar diagnóstico', now(), '891ff4e4-9e00-41e2-9dae-520e07bb0cdd');
INSERT INTO main.permission(permission_id,created_at,name,title,updated_at,module_id) VALUES('cbfbdec0-f734-4ccb-b484-2412e63fae93', now(), 'CHANGE_STATUS_DIAGNOSTIC', 'Cambiar estado del diagnóstico', now(), '891ff4e4-9e00-41e2-9dae-520e07bb0cdd');
INSERT INTO main.permission(permission_id,created_at,name,title,updated_at,module_id) VALUES('9a4ad1a2-2801-42fd-8ecf-df1acc7e1b04', now(), 'DELETE_DIAGNOSTIC', 'Eliminar entrada al diagnóstico', now(), '891ff4e4-9e00-41e2-9dae-520e07bb0cdd');
INSERT INTO main.permission(permission_id,created_at,name,title,updated_at,module_id) VALUES('a901da9c-1a69-4822-add8-caf8ab2197aa', now(), 'CHANGE_STATUS_DIAGNOSTIC_EXTRAORDINARY', 'Cambiar estado del diagnóstico de manera extraordinario', now(), '891ff4e4-9e00-41e2-9dae-520e07bb0cdd');
INSERT INTO main.permission(permission_id,created_at,name,title,updated_at,module_id) VALUES('0c4e216b-298a-43ef-a362-489dee3fecd5', now(), 'CHANGE_BATCH_DIAGNOSTIC', 'Cambiar lote del diagnostico', now(), '891ff4e4-9e00-41e2-9dae-520e07bb0cdd');
INSERT INTO main.permission(permission_id,created_at,name,title,updated_at,module_id) VALUES('407a758f-1d7d-4584-9556-1462c86b1527', now(), 'CHANGE_TECHNICAL_DIAGNOSTIC', 'Cambiar técnico del diagnostico', now(), '891ff4e4-9e00-41e2-9dae-520e07bb0cdd');
INSERT INTO main.permission(permission_id,created_at,name,title,updated_at,module_id) VALUES('8e8b79a7-49c0-4058-bce0-9dacdd8aec4a', now(), 'MASSIVELY_CHANGE_STATUS', 'Cambiar estado de manera masiva', now(), '891ff4e4-9e00-41e2-9dae-520e07bb0cdd');
INSERT INTO main.permission(permission_id,created_at,name,title,updated_at,module_id) VALUES('4d969382-5f86-4536-bdc0-d730ba3cf9c1', now(), 'DOWNLOAD_REPORT_QA_LAB', 'Descargar el reporte con la información del control de calidad', now(), '891ff4e4-9e00-41e2-9dae-520e07bb0cdd');

---------------------------- REPORT ---------------------
INSERT INTO main.permission(permission_id,created_at,name,title,updated_at,module_id) VALUES('857f87b7-69da-4ff2-a74b-654ad60c4e09', now(), 'CREATE_REPORT', 'Crear reporte', now(), 'dc664352-3c72-43da-ba90-d85d2305f70c');
INSERT INTO main.permission(permission_id,created_at,name,title,updated_at,module_id) VALUES('77d6cac9-4bce-49af-9d1f-99a5ac37937e', now(), 'GET_GENERAL_REPORT', 'Obtener el reporte general', now(), 'dc664352-3c72-43da-ba90-d85d2305f70c');

---------------------------- CLIENT ---------------------
INSERT INTO main.permission(permission_id,created_at,name,title,updated_at,module_id) VALUES('24c157fd-2554-43e1-b0c0-2c6efe5e1b01', now(), 'CREATE_CLIENT', 'Crear cliente', now(), '98679451-0fc7-4f37-b061-f12dc8b219e4');
INSERT INTO main.permission(permission_id,created_at,name,title,updated_at,module_id) VALUES('87564909-1f2f-4cef-8bf9-9b3263029a56', now(), 'GET_CLIENT', 'Ver clientes', now(), '98679451-0fc7-4f37-b061-f12dc8b219e4');
INSERT INTO main.permission(permission_id,created_at,name,title,updated_at,module_id) VALUES('0d1262a5-76e2-40bb-b7e3-4a09cc186ccf', now(), 'DISASSOCIATE_PARTS_OF_CLIENT', 'Desasociar repuestos del cliente', now(), '98679451-0fc7-4f37-b061-f12dc8b219e4');

---------------------------- PART ---------------------
INSERT INTO main.permission(permission_id,created_at,name,title,updated_at,module_id) VALUES('5fde8ac3-ae60-443f-b068-dd50fba0d4cd', now(), 'CREATE_PART', 'Registrar repuesto', now(), '17aa62b2-c5b2-4e96-934b-ae9cdd2a255d');
INSERT INTO main.permission(permission_id,created_at,name,title,updated_at,module_id) VALUES('b22979e3-85ec-42b4-8b0e-52a3f1176557', now(), 'GET_PART', 'ver repuestos', now(), '17aa62b2-c5b2-4e96-934b-ae9cdd2a255d');
INSERT INTO main.permission(permission_id,created_at,name,title,updated_at,module_id) VALUES('9b5309ec-233a-4637-b963-558238eb3aa8', now(), 'ASSOCIATE_PART_TO_CLIENTS', 'Asociar repuestos a clientes', now(), '17aa62b2-c5b2-4e96-934b-ae9cdd2a255d');

---------------------------- QA ---------------------
INSERT INTO main.permission(permission_id,created_at,name,title,updated_at,module_id) VALUES('c9a7e1bc-12f9-4b9f-9ac2-0da4fe527e3d', now(), 'GET_QA', 'Acceder y visualizar la información del módulo de control de calidad', now(), 'f5122215-f544-4a79-a87f-c4b07915a3ec');
INSERT INTO main.permission(permission_id,created_at,name,title,updated_at,module_id) VALUES('3078837e-1346-4187-a537-c500a97cac0d', now(), 'UPDATE_QA', 'Realizar la revisión técnica y validar el checklist de una terminal POS', now(), 'f5122215-f544-4a79-a87f-c4b07915a3ec');
INSERT INTO main.permission(permission_id,created_at,name,title,updated_at,module_id) VALUES('239d80b3-1384-4e71-aace-a8f1fe8920c9', now(), 'DOWNLOAD_REPORT_QA', 'Descargar el reporte con la información del control de calidad  módulo  QA', now(), 'f5122215-f544-4a79-a87f-c4b07915a3ec');
INSERT INTO main.permission(permission_id,created_at,name,title,updated_at,module_id) VALUES('0eece11a-8a52-4f91-b0a2-5f3646e056de', now(), 'UPDATE_STATUS_DIAGNOSTIC_QA', 'Actualizar estado actual del diagnostico de la terminal', now(), 'f5122215-f544-4a79-a87f-c4b07915a3ec');

