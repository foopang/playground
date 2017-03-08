import { Action } from '@ngrx/store';
import { PetTag, initialTag } from './../core/pet-tag.model';
import { SELECT_SHAPE, SELECT_FONT, ADD_TEXT, TOOGLE_CLIP, TOOGLE_GEMS, COMPLETE, RESET } from './../core/pet-tag.actions';

export function petTagReducer(state: PetTag = initialTag, action: Action) {
  switch (action.type) {
  case SELECT_SHAPE:
    return Object.assign({}, state, {
      shape: action.payload
    });
  case SELECT_FONT:
    return Object.assign({}, state, {
      font: action.payload
    });
  case ADD_TEXT:
    return Object.assign({}, state, {
      text: action.payload
    });
  case TOOGLE_CLIP:
    return Object.assign({}, state, {
      clip: !state.clip
    });
  case TOOGLE_GEMS:
    return Object.assign({}, state, {
      gems: !state.gems
    });
  case COMPLETE:
    return Object.assign({}, state, {
      complete: action.payload
    });
  case RESET:
    return Object.assign({}, state, initialTag);
  default:
    return state;
  }
}
