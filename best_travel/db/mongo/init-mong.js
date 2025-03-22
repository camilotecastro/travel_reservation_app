db.createUser({
  user: 'root',
  pwd: 'toor',
  roles: [
    {
      role: 'readWrite',
      db: 'testDB',
    },
  ],
});
db.createCollection('app_users', { capped: false });

db.app_users.insert([
  {
    "username": "ragnar777",
    "dni": "VIKI771012HMCRG093",
    "enabled": true,
    "password": "s3cr3t",
    "role":
        {
          "granted_authorities": ["ROLE_USER"]
        }
  },
  {
    "username": "heisenberg",
    "dni": "BBMB771012HMCRR022",
    "enabled": true,
    "password": "p4sw0rd",
    "role":
        {
          "granted_authorities": ["ROLE_USER"]
        }
  },
  {
    "username": "misterX",
    "dni": "GOTW771012HMRGR087",
    "enabled": true,
    "password": "misterX123",
    "role":
        {
          "granted_authorities": ["ROLE_USER", "ROLE_ADMIN"]
        }
  },
  {
    "username": "neverMore",
    "dni": "WALA771012HCRGR054",
    "enabled": true,
    "password": "4dmIn",
    "role":
        {
          "granted_authorities": ["ROLE_ADMIN"]
        }
  }
]);