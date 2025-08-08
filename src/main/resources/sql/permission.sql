INSERT INTO main."permission"(permission_id, created_at, module_id, permission_name, title, updated_at)VALUES('e16a43e6-b006-4f69-ba4a-138206906c93', now(), '21617961-244d-40ec-8142-b0c92cfb4a3b', 'CREATE_ROLE', 'Crear rol', now());
INSERT INTO main."permission"(permission_id, created_at, module_id, permission_name, title, updated_at)VALUES('a8d63be1-df90-4d84-9346-0e5397d915d7', now(), '21617961-244d-40ec-8142-b0c92cfb4a3b', 'EDIT_ROLE', 'Editar rol', now());
INSERT INTO main."permission"(permission_id, created_at, module_id, permission_name, title, updated_at)VALUES('f294b9c4-df0a-4f49-9853-7a0628f2abc0', now(), '21617961-244d-40ec-8142-b0c92cfb4a3b', 'DELETE_ROLE', 'Eliminar rol', now());



-------------------------- ROLE - PERMISO -------------------
INSERT INTO main.role_permission(permission_id, role_id)VALUES('e16a43e6-b006-4f69-ba4a-138206906c93', 'aedaa76a-acfe-4267-b43d-d4798bc8f36b');
INSERT INTO main.role_permission(permission_id, role_id)VALUES('a8d63be1-df90-4d84-9346-0e5397d915d7', 'aedaa76a-acfe-4267-b43d-d4798bc8f36b');
INSERT INTO main.role_permission(permission_id, role_id)VALUES('f294b9c4-df0a-4f49-9853-7a0628f2abc0', 'aedaa76a-acfe-4267-b43d-d4798bc8f36b');
