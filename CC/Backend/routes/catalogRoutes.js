module.exports = [
    {
      method: 'GET',
      path: '/catalog',
      handler: require('../handlers/catalogHandlers').getHandsigns
    }
  ];
  