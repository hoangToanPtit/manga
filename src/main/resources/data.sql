INSERT INTO manga.role(name)
SELECT * FROM (SELECT 'ROLE_USER') as tmp WHERE NOT EXISTS (SELECT * FROM manga.role WHERE role.name = 'ROLE_USER');
INSERT INTO manga.role(name)
SELECT * FROM (SELECT 'ROLE_MODERATOR') as tmp WHERE NOT EXISTS (SELECT * FROM manga.role WHERE role.name = 'ROLE_MODERATOR');
INSERT INTO manga.role(name)
SELECT * FROM (SELECT 'ROLE_ADMIN') as tmp WHERE NOT EXISTS (SELECT * FROM manga.role WHERE role.name = 'ROLE_ADMIN');

