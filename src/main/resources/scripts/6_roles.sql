------------------------------------------------------------------------------------------------------------------------
--Role Admin
INSERT INTO main.role(role_id, created_at, is_active, name, updated_at) VALUES ('e50411ea-ab77-453c-a75a-3b085f9eaef2', now(), true, 'Administrator', now());

--Admin
INSERT INTO main.user_role (user_id, role_id) VALUES ('9bbe2f54-b640-42f3-a26c-aebab9527549', 'e50411ea-ab77-453c-a75a-3b085f9eaef2');

-- Permissions Admin
INSERT INTO main.role_permission(role_id, permission_id) VALUES ('e50411ea-ab77-453c-a75a-3b085f9eaef2', '34698c89-3bd1-47a0-b225-6cd843bfb1a5'), ('e50411ea-ab77-453c-a75a-3b085f9eaef2', 'eed952cc-f4df-4ba8-944d-284f79fa5001'), ('e50411ea-ab77-453c-a75a-3b085f9eaef2', 'ddf47c42-2d13-4979-a5ba-58af17103b3d'), ('e50411ea-ab77-453c-a75a-3b085f9eaef2', '697bb69f-2aa1-42af-98c6-885827ffb083'), ('e50411ea-ab77-453c-a75a-3b085f9eaef2', '35c2c191-8d17-4dfb-81a3-2137bdf2a981'), ('e50411ea-ab77-453c-a75a-3b085f9eaef2', 'e4a82feb-8e54-415c-aa21-572ab5d641a3'), ('e50411ea-ab77-453c-a75a-3b085f9eaef2', 'bc02b771-4b33-494f-b629-d98d1c843cf3'), ('e50411ea-ab77-453c-a75a-3b085f9eaef2', '4b8111c9-3c22-4e8c-babc-f09d5a95ea8f');

-- Permissions Admin - Dashboard
INSERT INTO main.role_permission(role_id, permission_id) VALUES ('e50411ea-ab77-453c-a75a-3b085f9eaef2', '8010afdd-1d53-4ee6-9241-99d50bfcb2dd');

-- Permissions Admin - Terminal
INSERT INTO main.role_permission(role_id, permission_id) VALUES ('e50411ea-ab77-453c-a75a-3b085f9eaef2', 'ceddb31e-2b12-41d3-8dbd-f497ab099374'), ('e50411ea-ab77-453c-a75a-3b085f9eaef2', '3aedafe9-c971-4042-a614-979f286492ea'), ('e50411ea-ab77-453c-a75a-3b085f9eaef2', 'fd33859a-d81c-4304-bd10-1b27ad239321');

-- Permissions Admin - Diagnostic
INSERT INTO main.role_permission(role_id, permission_id) VALUES ('e50411ea-ab77-453c-a75a-3b085f9eaef2', '4c5ef7e9-e021-4bf5-a6e6-5261aeab7726'), ('e50411ea-ab77-453c-a75a-3b085f9eaef2', 'a8836bde-c811-461b-9720-13be40306067'), ('e50411ea-ab77-453c-a75a-3b085f9eaef2', '5b05abdc-3e25-4cd2-85cc-cb52f3558b5b'), ('e50411ea-ab77-453c-a75a-3b085f9eaef2', '9f55afd5-1a98-41dc-84d2-6f2948901cb3'), ('e50411ea-ab77-453c-a75a-3b085f9eaef2', 'cbfbdec0-f734-4ccb-b484-2412e63fae93'), ('e50411ea-ab77-453c-a75a-3b085f9eaef2', 'beb6061e-dc6f-4015-9663-0283b1c201f8');
INSERT INTO main.role_permission(role_id, permission_id) VALUES ('e50411ea-ab77-453c-a75a-3b085f9eaef2', '4d969382-5f86-4536-bdc0-d730ba3cf9c1');
-- Permissions Admin - Report
INSERT INTO main.role_permission(role_id, permission_id) VALUES ('e50411ea-ab77-453c-a75a-3b085f9eaef2', '857f87b7-69da-4ff2-a74b-654ad60c4e09');
-- Permissions Admin - Report General
INSERT INTO main.role_permission(role_id, permission_id) VALUES ('e50411ea-ab77-453c-a75a-3b085f9eaef2', '77d6cac9-4bce-49af-9d1f-99a5ac37937e');

------------------------------------------------------------------------------------------------------------------------
--Role Technical
INSERT INTO main.role(role_id, created_at, is_active, name, updated_at) VALUES ('2e16c30c-a4d7-4d42-ac54-1848988e4fbb', now(), true, 'Técnico', now());

--Technical
insert into main.user_role (user_id, role_id) VALUES ('d67349e0-7889-49d7-ac90-e365a88ad5a1', '2e16c30c-a4d7-4d42-ac54-1848988e4fbb');

-- Permissions Technical - Diagnostic
INSERT INTO main.role_permission(role_id, permission_id) VALUES ('2e16c30c-a4d7-4d42-ac54-1848988e4fbb', '4c5ef7e9-e021-4bf5-a6e6-5261aeab7726'), ('2e16c30c-a4d7-4d42-ac54-1848988e4fbb', '5b05abdc-3e25-4cd2-85cc-cb52f3558b5b'), ('2e16c30c-a4d7-4d42-ac54-1848988e4fbb', 'cbfbdec0-f734-4ccb-b484-2412e63fae93');

--Permissions Admin - parts - clients
INSERT INTO main.role_permission(role_id, permission_id) VALUES('e50411ea-ab77-453c-a75a-3b085f9eaef2', '24c157fd-2554-43e1-b0c0-2c6efe5e1b01'), ('e50411ea-ab77-453c-a75a-3b085f9eaef2', '87564909-1f2f-4cef-8bf9-9b3263029a56'), ('e50411ea-ab77-453c-a75a-3b085f9eaef2', '0d1262a5-76e2-40bb-b7e3-4a09cc186ccf'), ('e50411ea-ab77-453c-a75a-3b085f9eaef2', '5fde8ac3-ae60-443f-b068-dd50fba0d4cd'), ('e50411ea-ab77-453c-a75a-3b085f9eaef2', 'b22979e3-85ec-42b4-8b0e-52a3f1176557'), ('e50411ea-ab77-453c-a75a-3b085f9eaef2', '9b5309ec-233a-4637-b963-558238eb3aa8');

--Permissions Admin - QA
INSERT INTO main.role_permission(role_id, permission_id) VALUES('e50411ea-ab77-453c-a75a-3b085f9eaef2', 'c9a7e1bc-12f9-4b9f-9ac2-0da4fe527e3d'), ('e50411ea-ab77-453c-a75a-3b085f9eaef2', '3078837e-1346-4187-a537-c500a97cac0d'), ('e50411ea-ab77-453c-a75a-3b085f9eaef2', '239d80b3-1384-4e71-aace-a8f1fe8920c9'), ('e50411ea-ab77-453c-a75a-3b085f9eaef2', '0eece11a-8a52-4f91-b0a2-5f3646e056de');
