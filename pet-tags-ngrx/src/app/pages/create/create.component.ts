import { Component, OnInit, OnDestroy } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { Subscription } from 'rxjs/Subscription';
import { Store } from '@ngrx/store';
import { SELECT_SHAPE, SELECT_FONT, ADD_TEXT, TOOGLE_CLIP, TOOGLE_GEMS, COMPLETE, RESET } from './../../core/pet-tag.actions';
import { PetTag } from './../../core/pet-tag.model';
import { AuthService } from './../../core/auth.service';

@Component({
  selector: 'app-create',
  templateUrl: './create.component.html'
})
export class CreateComponent implements OnInit, OnDestroy {
  tagState$: Observable<PetTag>;
  private tagStateSubscription: Subscription;
  petTag: PetTag;
  done = false;

  constructor(private store: Store<PetTag>, public auth: AuthService) {
    this.tagState$ = store.select('petTag');
  }

  ngOnInit() {
    this.tagStateSubscription = this.tagState$.subscribe((state) => {
      this.petTag = state;
      this.done = !!(this.petTag.shape && this.petTag.text);
    });
  }

  ngOnDestroy() {
    this.tagStateSubscription.unsubscribe();
  }

  selectShapeHandler(shape: string) {
    this.store.dispatch({
      type: SELECT_SHAPE,
      payload: shape
    });
  }

  selectFontHandler(fontType: string) {
    this.store.dispatch({
      type: SELECT_FONT,
      payload: fontType
    });
  }

  addTextHandler(text: string) {
    this.store.dispatch({
      type: ADD_TEXT,
      payload: text
    });
  }

  toggleClipHandler() {
    this.store.dispatch({
      type: TOOGLE_CLIP
    });
  }

  toggleGemsHandler() {
    this.store.dispatch({
      type: TOOGLE_GEMS
    });
  }

  submit() {
    this.store.dispatch({
      type: COMPLETE,
      payload: true
    });
  }
}
