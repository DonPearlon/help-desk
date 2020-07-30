import React, { Component } from "react";
class LogOut extends Component {
  handleBack = () => {
    this.props.history.goBack();
  };
  render() {
    return (
      <div
        className="container"
        style={{
          marginTop: "5%"
        }}
      >
        <div className="col-10 mx-auto col-md-8 mt-4">
          <h4>You are currently logged as: {this.props.user.email}.</h4>
          <h4
            style={{
              marginBottom: "10%"
            }}
          >
            Are you sure you want to log out?
          </h4>
          <div className="d-flex justify-content-between my-2">
            <button
              type="button"
              className="btn btn-success "
              onClick={this.handleBack}
            >
              Back
            </button>
            <button
              type="button"
              className="btn btn-danger "
              onClick={this.props.handleLogOut}
            >
              Log out
            </button>
          </div>
        </div>
      </div>
    );
  }
}
export default LogOut;
