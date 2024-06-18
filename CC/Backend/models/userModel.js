const { Firestore } = require('@google-cloud/firestore');

const firestore = new Firestore({
});


  async function createUser(userData) {
    const userRef = firestore.collection('users').doc();

    //check for existing username
    const existingUser = await getUserByUsername(userData.username);
    const existingEmail = await getUserByEmail(userData.email);

    if (!existingUser && !existingEmail) {
      await userRef.set(userData);
      return userRef.id;
    }
    return null;
  }

  async function getUserByUsername(username) {
    const snapshot = await firestore.collection('users').where('username', '==', username).get();
    if (!snapshot.empty) {
      return snapshot.docs[0].data();
    }
    return null; 
  }

  async function getUserByEmail(email) {
    const snapshot = await firestore.collection('users').where('email', '==', email).get();
    if (!snapshot.empty) {
      return snapshot.docs[0].data();
    }
    return null; 
  }

  // ... functions for updateUser and deleteUser (tentative)

module.exports = {
  createUser,
  getUserByUsername,
  getUserByEmail,
  // updateUser,
  // deleteUser,
};
