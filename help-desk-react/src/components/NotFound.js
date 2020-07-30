import React, { Component } from "react";
class NotFound extends Component {
  handleBack = () => {
    this.props.history.push("/help-desk");
  };
  render() {
    return (
      <div className="container">
        <div className="col-10 mx-auto col-md-8 mt-4">
          <h1>Error :(</h1>
          <h1>Error code: 404</h1>
          <h3>The requested resource was not found</h3>
        </div>
        <button
          type="button"
          className="btn btn-success "
          onClick={this.handleBack}
        >
          Back
        </button>
      </div>
    );
  }
}
export default NotFound;
