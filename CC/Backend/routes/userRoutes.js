module.exports = [
    {
      method: 'POST',
      path: '/register',
      handler: require('../handlers/userHandlers').registerUser,
    },
    {
      method: 'POST',
      path: '/login',
      handler: require('../handlers/userHandlers').loginUser,
    },
    {
      method: 'POST',
      path: '/logout',
      handler: require('../handlers/userHandlers').logoutUser,
    },
  ];
  