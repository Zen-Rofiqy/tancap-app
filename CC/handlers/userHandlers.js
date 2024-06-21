const userModel = require("../models/userModel");
const bcrypt = require("bcrypt");
const jwt = require("jsonwebtoken");
const Joi = require("joi");

// ... (functions for hashing/comparing passwords, generating JWTs)

const registerUser = async (request, h) => {
  try {
    const userData = request.payload;

    // Input validation (username, email, password)
    // Enhanced Input Validation (Joi is a popular option)
    // const { error } = Joi.object({
    //   username: Joi.string().required(),
    //   email: Joi.string().email().required(),
    //   password: Joi.string().required(), // Enforce strong passwords
    // }).validate(userData);

    // if (error) {
    //   return h.response({ error: error.details[0].message }).code(400); // Bad Request
    // }

    const hashedPassword = await bcrypt.hash(userData.password, 10); // Salt rounds = 10

    const user = await userModel.createUser({
      ...userData,
      passwordHash: hashedPassword,
    });

    if (!user) {
      return h.response({ message: "Credentials already exists" }).code(409); // Conflict
    }
    
    return h.response({ message: "User registered successfully" }).code(201); // Created

  } catch (error) {
    console.error("Error registering user:", error);
    return h.response({ message: "Error registering user" }).code(500); // Internal Server Error
  }
};

const loginUser = async (request, h) => {
  try {
    const { email, password } = request.payload;

    // Input validation

    const user = await userModel.getUserByEmail(email);

    if (!user || !(await bcrypt.compare(password, user.passwordHash))) {
      return h.response({ message: "Invalid credentials" }).code(401);
    }

    const token = jwt.sign({ userId: user.id }, process.env.JWT_SECRET, {
      algorithm: "HS256",
      expiresIn: "1d",
    });
    return h.response({ token }).code(200);
  } catch (error) {
    // Error handling
    console.error("Error logging in user:", error);
    return h.response({ message: "Error logging in user" }).code(500);
  }
};

const logoutUser = (request, h) => {
  // In this approach, the server doesn't take any action.
  // The client is responsible for removing the token.
  return h.response({ message: "Logged out successfully" }).code(200);
};

module.exports = {
  registerUser,
  loginUser,
  logoutUser,
};
