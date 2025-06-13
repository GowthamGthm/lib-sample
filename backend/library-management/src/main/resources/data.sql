INSERT INTO users (username, password, email, full_name, created_at)
VALUES (
  'admin',
  '$2a$10$wW6A0gxLBBUnBbBRmxb3aeU7eR5Ho/W6p3sQdXPfxC7Z95hM6AfVi',
  'admin@gmail.com',
  'Admin User',
  now()
)
ON CONFLICT (username) DO NOTHING;