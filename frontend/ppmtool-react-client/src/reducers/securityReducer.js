import { SET_CURRENT_USER } from "../actions/types";
const intitialState = {
  user: {},
  validToken: false
};
const checkPayload = payload => {
  if (payload) return true;
  else return false;
};
export default function(state = intitialState, action) {
  switch (action.type) {
    case SET_CURRENT_USER:
      return {
        ...state,
        validToken: checkPayload(action.payload),
        user: action.payload
      };
  }
}
