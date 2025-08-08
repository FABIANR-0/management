-- Modules
INSERT INTO main.module (module_order, created_at, updated_at, module_id, parent_id, name, route, description, primary_icon, secondary_icon, tertiary_icon) VALUES (1, now(), now(), '43eedff1-7cc0-4614-a4b2-dc0443d20c64', null, 'Dashboard', 'dashboard', 'Visualiza un resumen general con métricas clave para toma de decisiones', 'icon-dashboard', 'icon-bar-chart', 'icon-pie-chart');
INSERT INTO main.module (module_order, created_at, updated_at, module_id, parent_id, name, route, description, primary_icon, secondary_icon, tertiary_icon) VALUES (2, now(), now(), 'f58bbc71-0082-414e-ba52-666427bce16e', null, 'Roles', 'role', 'Gestiona los diferentes roles de usuario y sus permisos dentro del sistema', 'icon-task-list', 'icon-shield-check', 'icon-make-group');
INSERT INTO main.module (module_order, created_at, updated_at, module_id, parent_id, name, route, description, primary_icon, secondary_icon, tertiary_icon) VALUES (3, now(), now(), 'b71340e8-b44b-477f-8475-af42c580668b', null, 'Usuarios', 'user', 'Administra la lista de usuarios, sus datos y niveles de acceso', 'icon-user-plus', 'icon-users', 'icon-padlock');
INSERT INTO main.module (module_order, created_at, updated_at, module_id, parent_id, name, route, description, primary_icon, secondary_icon, tertiary_icon) VALUES (4, now(), now(), '891ff4e4-9e00-41e2-9dae-520e07bb0cdd', null, 'Laboratorio', 'laboratory', 'Supervisa y organiza el laboratorio para un mejor control y gestión', 'icon-tools', 'icon-wrench', 'icon-pencil2');
INSERT INTO main.module (module_order, created_at, updated_at, module_id, parent_id, name, route, description, primary_icon, secondary_icon, tertiary_icon) VALUES (5, now(), now(), 'f5122215-f544-4a79-a87f-c4b07915a3ec', null, 'QA', 'qa', 'Revisa y valida los diagnósticos mediante un checklist de control de calidad.', 'icon-clipboard', 'icon-search', 'icon-pos-terminal');
INSERT INTO main.module (module_order, created_at, updated_at, module_id, parent_id, name, route, description, primary_icon, secondary_icon, tertiary_icon) VALUES (6, now(), now(), 'a5bc123b-d86f-4450-9e52-cb635af7c8a2', null, 'Terminales', 'terminal', 'Carga y administra el inventario de terminales fácilmente', 'icon-pos-terminal', 'icon-tree1', 'icon-branch');
INSERT INTO main.module (module_order, created_at, updated_at, module_id, parent_id, name, route, description, primary_icon, secondary_icon, tertiary_icon) VALUES (7, now(), now(), '98679451-0fc7-4f37-b061-f12dc8b219e4', null, 'Clientes', 'client', 'Administra la lista de clientes de la plataforma', 'icon-bank', 'icon-users', 'icon-clipboard');
INSERT INTO main.module (module_order, created_at, updated_at, module_id, parent_id, name, route, description, primary_icon, secondary_icon, tertiary_icon) VALUES (8, now(), now(), '17aa62b2-c5b2-4e96-934b-ae9cdd2a255d', null, 'Repuestos', 'part', 'Administra los repuestos registrados en el sistema', 'icon-box', 'icon-cogs', 'icon-wrench');
INSERT INTO main.module (module_order, created_at, updated_at, module_id, parent_id, name, route, description, primary_icon, secondary_icon, tertiary_icon) VALUES (9, now(), now(), 'dc664352-3c72-43da-ba90-d85d2305f70c', null, 'Reportes', 'report', 'Genera informes detallados con datos clave', 'icon-file-pdf', 'icon-files', 'icon-file-excel');

-- Types
INSERT INTO main.type (type_id, type_name) VALUES ('177ab30b-4336-4a56-8f12-182a91c3edc1', 'Administrator');
INSERT INTO main.type (type_id, type_name) VALUES ('b618f794-32cd-4558-97ff-227bbcf707dc', 'Technical');

-- Module Types
INSERT INTO main.module_type (module_id, type_id) VALUES ('f58bbc71-0082-414e-ba52-666427bce16e', '177ab30b-4336-4a56-8f12-182a91c3edc1');
INSERT INTO main.module_type (module_id, type_id) VALUES ('43eedff1-7cc0-4614-a4b2-dc0443d20c64', '177ab30b-4336-4a56-8f12-182a91c3edc1');
INSERT INTO main.module_type (module_id, type_id) VALUES ('891ff4e4-9e00-41e2-9dae-520e07bb0cdd', '177ab30b-4336-4a56-8f12-182a91c3edc1');
INSERT INTO main.module_type (module_id, type_id) VALUES ('b71340e8-b44b-477f-8475-af42c580668b', '177ab30b-4336-4a56-8f12-182a91c3edc1');
INSERT INTO main.module_type (module_id, type_id) VALUES ('a5bc123b-d86f-4450-9e52-cb635af7c8a2', '177ab30b-4336-4a56-8f12-182a91c3edc1');
INSERT INTO main.module_type (module_id, type_id) VALUES ('dc664352-3c72-43da-ba90-d85d2305f70c', '177ab30b-4336-4a56-8f12-182a91c3edc1');
INSERT INTO main.module_type (module_id, type_id) VALUES ('98679451-0fc7-4f37-b061-f12dc8b219e4', '177ab30b-4336-4a56-8f12-182a91c3edc1');
INSERT INTO main.module_type (module_id, type_id) VALUES ('17aa62b2-c5b2-4e96-934b-ae9cdd2a255d', '177ab30b-4336-4a56-8f12-182a91c3edc1');
INSERT INTO main.module_type (module_id, type_id) VALUES ('f5122215-f544-4a79-a87f-c4b07915a3ec', '177ab30b-4336-4a56-8f12-182a91c3edc1');

INSERT INTO main.module_type (module_id, type_id) VALUES ('a5bc123b-d86f-4450-9e52-cb635af7c8a2', 'b618f794-32cd-4558-97ff-227bbcf707dc');
