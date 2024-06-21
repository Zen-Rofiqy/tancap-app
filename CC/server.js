const Hapi = require('@hapi/hapi');
const catalogRoutes = require('./routes/catalogRoutes');
const userRoutes = require('./routes/userRoutes');
// const testFirestoreConnection = require('./routes/testRoutes');
// require('dotenv').config();

// Load environment variables from .env file

const server = Hapi.server({
  port: process.env.PORT || 3000,
  host: '0.0.0.0', // For App Engine, use 0.0.0.0
  routes: {
    cors: {
      origin: ['*'],
    },
  }
});

server.route([...catalogRoutes, ...userRoutes]);

const init = async () => {
  await server.start();
  console.log('Server running on %s', server.info.uri);
};

process.on('unhandledRejection', (err) => {
  console.log(err);
  process.exit(1);
});

init();
