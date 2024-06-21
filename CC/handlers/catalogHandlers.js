const { Storage } = require('@google-cloud/storage');
const { Firestore } = require('@google-cloud/firestore');

// Initialize GCP clients
// const storage = new Storage();
const firestore = new Firestore();

const getHandsigns = async (request, h) => {
  try {
    const handsignsRef = firestore.collection('handsigns-l');
    const snapshot = await handsignsRef.get();

    const catalogEntries = [];
    snapshot.forEach(doc => {
      const data = doc.data();
      const gcsUrl = `https://storage.googleapis.com/${process.env.GCS_BUCKET_NAME}/handsigns-l/${data.imageName}`;
      catalogEntries.push({
        image: gcsUrl,
        description: data.imageDesc,
      });
    });

    // catalogEntries.sort((a, b) => {
    //   const nameA = a.gcsUrl.toLowerCase();  // Get image name in lowercase
    //   const nameB = b.gcsUrl.toLowerCase();

    //   if (nameA === 'https://storage.googleapis.com/${bucketName}/handsigns-l/space.jpg') return 1; // Space.jpg goes last
    //   if (nameB === 'https://storage.googleapis.com/${bucketName}/handsigns-l/space.jpg') return -1;

    //   // 2. Compare Alphabetically:
    //   return nameA.localeCompare(nameB);
    // });

    return h.response(catalogEntries).code(200);
  } catch (error) {
    console.error("Error fetching catalog:", error);
    return h.response({ error: 'Failed to retrieve catalog data' }).code(500);
  }
};

module.exports = {
  getHandsigns,
};
