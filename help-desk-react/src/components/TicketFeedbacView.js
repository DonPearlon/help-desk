import React, { Component } from "react";
import RateBar from "./RateBar";
import axios from "axios";

class TicketFeedbackView extends Component {
  constructor(props) {
    super(props);
    this.state = {
      ticketId: this.props.match.params.id,
      user: this.props.user,
      feedback: ""
    };
  }

  componentDidMount() {
    this.getFeedback();
  }

  getFeedback() {
    const userId = this.state.user.id;
    const ticketId = this.state.ticketId;
    const authorization = this.props.getAuthorization();
    axios
      .get(
        "http://localhost:8080/help-desk/api/users/" +
          userId +
          "/tickets/" +
          ticketId +
          "/feedback",
        {
          headers: { authorization: authorization }
        }
      )
      .then(response => {
        this.setState({ feedback: response.data }, () => {
          console.log(this.state);
        });
      })
      .catch(() => {
        this.props.history.push("/not-found");
      });
  }

  handleBack = () => {
    this.props.history.goBack();
  };

  render() {
    return (
      <div className="container ">
        <div className="row">
          <div className="col-10 mx-auto col-md-8 mt-4">
            <button
              type="button"
              className="btn btn-success "
              onClick={this.handleBack}
            >
              Back
            </button>
            <h1 className="text-capitalize text-center">
              Ticket: {this.state.feedback.ticketName}
            </h1>
            <div
              style={{
                marginBottom: "5%"
              }}
            ></div>
            <div className="col-10 mx-auto col-md-8 mt-4">
              <h4 className="text-left">Feedback.</h4>
              <h4
                className="text-center"
                style={{
                  marginBottom: "5%"
                }}
              >
                Solution rate: {this.state.feedback.rate}.
              </h4>

              <RateBar
                key={this.state.feedback.rate}
                rate={this.state.feedback.rate}
              />
              <form
                style={{
                  marginTop: "15%"
                }}
              >
                <div className="form-group row">
                  <div className="col-12">
                    <textarea
                      className="form-control"
                      id="commentTextarea"
                      rows="5"
                      readOnly
                      onChange={this.handleChangeComment}
                      value={this.state.feedback.text}
                    ></textarea>
                  </div>
                </div>
              </form>
              <h4 className="text-left">Left: {this.state.feedback.date}</h4>
            </div>
          </div>
        </div>
      </div>
    );
  }
}
export default TicketFeedbackView;
