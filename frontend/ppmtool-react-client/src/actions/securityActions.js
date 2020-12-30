import axios from "axios";
import setJwtToken from "../securityUtils/setJwtToken";
import { GET_ERRORS } from "./types";
import jwt_decode from "jwt-decode";
export const createNewUser = (newUser, history) => async dispatch => {
  try {
    await axios.post("", newUser);
    history.push("/login");
    dispatch({
      type: GET_ERRORS,
      payload: {}
    });
  } catch (err) {
    dispatch({
      type: GET_ERRORS,
      payload: err.response.data
    });
  }
};
export const login = LoginRequest => async dispatch => {
  try {
    const res = await axios.post("api/users/post/login", LoginRequest);
    const { token } = res.data;
    localStorage.setItem("jwtToken", token);
    setJwtToken(token);
    const deoded = jwt_decode(token);
    dispatch({
      type: GET_ERRORS,
      payload: {}
    });
  } catch (error) {
    dispatch({
      type: GET_ERRORS,
      payload: error.response.data
    });
  }
};
