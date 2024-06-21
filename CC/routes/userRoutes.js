module.exports = [
  {
    method: 'POST',
    path: '/api/register', // Added /api
    handler: require('../handlers/userHandlers').registerUser,
  },
  {
    method: 'POST',
    path: '/api/login', // Added /api
    handler: require('../handlers/userHandlers').loginUser,
  },
  {
    method: 'POST',
    path: '/api/logout', // Added /api
    handler: require('../handlers/userHandlers').logoutUser,
  },
];
