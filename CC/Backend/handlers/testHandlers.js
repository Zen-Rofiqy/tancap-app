// const { Firestore } = require('@google-cloud/firestore');

// const firestore = new Firestore(
// ); // Initialize Firestore

// const testFirestoreConnection = async (request, h) => {
//     try {
//         const collections = await firestore.listCollections();
//         const collectionIds = collections.map((collection) => collection.id);
//         let response = h.response({ message: 'Firestore connection successful', collectionIds});
//         response.code(200);
//         return response;
//     } catch (error) {
//         console.error('Error connecting to Firestore:', error);
//         response = h.response({ message: 'Firestore connection failed' });
//         response.code(500);
//         return response;
//     }
// };

// module.exports = {
//     testFirestoreConnection,
// };
