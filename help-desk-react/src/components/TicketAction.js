import React, { Component } from "react";
import axios from "axios";

class TicketAction extends Component {
  constructor(props) {
    super(props);
    this.state = {
      userId: this.props.userId,
      ticketId: this.props.ticketId,
      actions: [],
      leaveFeedback: "7",
      viewFeedback: "8"
    };
  }

  componentDidMount() {
    this.getActions();
  }

  getActions = () => {
    const userId = this.state.userId;
    const ticketId = this.state.ticketId;
    const authorization = this.props.getAuthorization();
    axios
      .get(
        "http://localhost:8080/help-desk/api/users/" +
          userId +
          "/tickets/" +
          ticketId +
          "/actions",
        {
          headers: { authorization: authorization }
        }
      )
      .then(response => {
        this.setState({ actions: response.data }, () => {
          console.log(this.state);
        });
      });
  };
  performTicketAction = () => {
    const actionId = this.selectId.value;
    const ticketId = this.state.ticketId;
    if (actionId === this.state.leaveFeedback) {
      this.props.handleFeedbackLeave(ticketId);
    } else if (actionId === this.state.viewFeedback) {
      this.props.handleFeedbackView(ticketId);
    } else {
      this.props.performTicketAction(ticketId, actionId);
    }
  };

  render() {
    const ActionSelect = () => (
      <>
        {this.state.actions.length > 0 ? (
          <span className="mx-2 text-success">
            <select id="selectId" ref={ref => (this.selectId = ref)}>
              {this.state.actions.map((action, i) => (
                <option key={i} value={action.id}>
                  {action.name}
                </option>
              ))}
            </select>

            <span> </span>
            <button
              className="btn btn-success btn-sm "
              onClick={this.performTicketAction}
            >
              Action
            </button>
          </span>
        ) : (
          <></>
        )}
      </>
    );
    return (
      <div className="container">
        <ActionSelect />
      </div>
    );
  }
}
export default TicketAction;
