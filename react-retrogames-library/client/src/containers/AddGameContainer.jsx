import React, { Component } from 'react';
import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';
import { hashHistory } from 'react-router';
import { Form } from './../components';
import * as gamesActionCreators from '../actions/games';
import * as filestackActionCreators from '../actions/filestack';

class AddGameContainer extends Component {
  constructor (props) {
    super(props);
    this.submit = this.submit.bind(this);
    this.uploadPicture = this.uploadPicture.bind(this);
  }

  submit (event) {
    event.preventDefault();
    this.props.gameActions.postGame();
    hashHistory.push('/games');
  }

  uploadPicture () {
    this.props.filestackActions.uploadPicture();
  }

  render () {
    const { picture } = this.props;
    return <Form handleSubmit={this.submit} picture={picture} uploadPicture={this.uploadPicture} />
  }
}

export default connect (
  (state) => {
    return {
      picture: state.getIn(['filestack', 'url'], '')
    };
  },
  (dispatch) => {
    return {
      gameActions: bindActionCreators(gamesActionCreators, dispatch),
      filestackActions: bindActionCreators(filestackActionCreators, dispatch)
    };
  }
)(AddGameContainer);
