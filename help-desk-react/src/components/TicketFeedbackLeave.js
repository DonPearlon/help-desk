import React, { Component } from "react";
import RateBar from "./RateBar";
import axios from "axios";

class TicketFeedbackLeave extends Component {
  constructor(props) {
    super(props);
    this.state = {
      ticketId: this.props.match.params.id,
      user: this.props.user,
      ticket: "",
      rate: "",
      text: "",
      errorMessages: []
    };
  }

  componentDidMount() {
    this.getTicket();
  }

  getTicket() {
    const userId = this.state.user.id;
    const ticketId = this.state.ticketId;
    const authorization = this.props.getAuthorization();
    axios
      .get(
        "http://localhost:8080/help-desk/api/users/" +
          userId +
          "/tickets/" +
          ticketId,
        {
          headers: { authorization: authorization }
        }
      )
      .then(response => {
        this.setState({ ticket: response.data }, () => {
          console.log(this.state);
        });
      })
      .catch(() => {
        this.props.history.push("/not-found");
      });
  }

  leaveFeedback = () => {
    const userId = this.state.user.id;
    const ticketId = this.state.ticketId;
    const authorization = this.props.getAuthorization();
    axios
      .post(
        "http://localhost:8080/help-desk/api/users/" +
          userId +
          "/tickets/" +
          ticketId +
          "/feedback",
        {
          ticketId: this.state.ticketId,
          rate: this.state.rate,
          text: this.state.text
        },
        {
          headers: { authorization: authorization }
        }
      )
      .then(() => {
        this.handleBack();
      })
      .catch(error => {
        let errors = error.response.data.errors;
        this.setState({
          errorMessages: errors
        });
      });
  };
  handleChangeRate = e => {
    this.setState({
      rate: e.target.value
    });
  };
  handleChangeText = e => {
    this.setState({
      text: e.target.value
    });
  };
  handleBack = () => {
    this.props.history.goBack();
  };

  showRate = () => {
    alert("Rate: " + this.state.rate);
  };
  render() {
    const ErrorMessagesOutput = () => (
      <>
        {this.state.errorMessages.map(message => (
          <h6 className="text-danger" key={1 + Math.random()}>
            Error: {message}
          </h6>
        ))}
      </>
    );
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
              Ticket: {this.state.ticket.name}
            </h1>
            &nbsp;&nbsp;&nbsp;
            <div
              style={{
                marginBottom: "10%"
              }}
            >
              <ErrorMessagesOutput />
              <h4 className="text-center">
                Please, rate your satisfaction with the solution:
              </h4>
            </div>
            <div className="col-10 mx-auto col-md-8 mt-4">
              <div className="d-flex justify-content-between">
                <div className="form-check form-check-inline">
                  <input
                    type="radio"
                    className="form-check-input"
                    id="rate1"
                    name="ticketServiceRate"
                    value={1}
                    onChange={this.handleChangeRate}
                    style={{
                      width: "30px",
                      height: "30px"
                    }}
                  />
                  <label className="form-check-label" htmlFor="materialInline1">
                    <h1>1</h1>
                  </label>
                </div>
                <div className="form-check form-check-inline">
                  <input
                    type="radio"
                    className="form-check-input"
                    id="rate2"
                    name="ticketServiceRate"
                    value={2}
                    onChange={this.handleChangeRate}
                    style={{
                      width: "30px",
                      height: "30px"
                    }}
                  />
                  <label className="form-check-label" htmlFor="materialInline2">
                    <h1>2</h1>
                  </label>
                </div>
                <div className="form-check form-check-inline">
                  <input
                    type="radio"
                    className="form-check-input"
                    id="rate3"
                    name="ticketServiceRate"
                    value={3}
                    onChange={this.handleChangeRate}
                    style={{
                      width: "30px",
                      height: "30px"
                    }}
                  />
                  <label className="form-check-label" htmlFor="materialInline1">
                    <h1>3</h1>
                  </label>
                </div>
                <div className="form-check form-check-inline">
                  <input
                    type="radio"
                    className="form-check-input"
                    id="rate4"
                    name="ticketServiceRate"
                    value={4}
                    onChange={this.handleChangeRate}
                    style={{
                      width: "30px",
                      height: "30px"
                    }}
                  />
                  <label className="form-check-label" htmlFor="materialInline1">
                    <h1>4</h1>
                  </label>
                </div>

                <div className="form-check form-check-inline">
                  <input
                    type="radio"
                    className="form-check-input"
                    id="rate5"
                    name="ticketServiceRate"
                    value={5}
                    onChange={this.handleChangeRate}
                    style={{
                      width: "30px",
                      height: "30px"
                    }}
                  />
                  <label className="form-check-label" htmlFor="materialInline1">
                    <h1>5</h1>
                  </label>
                </div>
              </div>
              <RateBar key={this.state.rate} rate={this.state.rate} />
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
                      onChange={this.handleChangeText}
                    ></textarea>
                  </div>
                </div>
              </form>
            </div>
            <div className="d-flex justify-content-end m-2">
              <button
                type="button"
                className="btn btn-success"
                style={{
                  marginTop: "5%"
                }}
                onClick={this.leaveFeedback}
              >
                Submit
              </button>
            </div>
          </div>
        </div>
      </div>
    );
  }
}
export default TicketFeedbackLeave;
