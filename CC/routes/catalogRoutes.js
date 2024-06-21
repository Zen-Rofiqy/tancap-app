module.exports = [
  {
    method: 'GET',
    path: '/api/catalog', 
    handler: require('../handlers/catalogHandlers').getHandsigns
  }
];
