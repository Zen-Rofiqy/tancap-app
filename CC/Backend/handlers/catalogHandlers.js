const { Storage } = require('@google-cloud/storage');
const { Firestore } = require('@google-cloud/firestore');

// Initialize GCP clients
const storage = new Storage();
const firestore = new Firestore();

const bucketName = process.env.GCS_BUCKET_NAME;

const getHandsigns = async (request, h) => {
  try {
    const handsignsRef = firestore.collection('handsigns');
    const snapshot = await handsignsRef.get();

    const catalogEntries = [];
    snapshot.forEach(doc => {
      const data = doc.data();
      const gcsUrl = `https://storage.googleapis.com/${bucketName}/handsigns/${data.imageName}`;
      catalogEntries.push({
        imageId: doc.id,
        gcsUrl,
        description: data.description,
        tags: data.tags || [],
        // ... other metadata fields
      });
    });

    return h.response(catalogEntries).code(200);
  } catch (error) {
    console.error("Error fetching catalog:", error);
    return h.response({ error: 'Failed to retrieve catalog data' }).code(500);
  }
};

module.exports = {
  getHandsigns,
};
