import React, { Component } from "react";
import { Route, Switch } from "react-router";
import LogIn from "./components/LogIn";
import HelpDesk from "./components/HelpDesk";
import Ticket from "./components/Ticket";
import CreateTicket from "./components/CreateTicket";
import EditTicket from "./components/EditTicket";
import TicketFeedbackLeave from "./components/TicketFeedbackLeave";
import TicketFeedbackView from "./components/TicketFeedbacView";
import LogOut from "./components/LogOut";
import NotFound from "./components/NotFound";
import "./bootstrap/css/bootstrap.min.css";

class App extends Component {
  constructor(props) {
    super(props);
    this.state = {
      user: null,
      token: null,
      tokenPrefix: "Bearer ",
      isAuthenticated: false
    };
  }
  componentDidMount() {
    let user = localStorage.getItem("user");
    let token = localStorage.getItem("token");
    if (user !== "null" && token !== "null") {
      this.setState({
        user: JSON.parse(user),
        token: token,
        isAuthenticated: true
      });
    }
  }

  handleLogIn = (user, token) => {
    this.setState({
      user: user,
      token: token,
      isAuthenticated: true
    });
    localStorage.setItem("user", JSON.stringify(user));
    localStorage.setItem("token", token);
  };
  handleLogOut = () => {
    this.setState({
      user: null,
      token: null,
      isAuthenticated: false
    });
    localStorage.setItem("user", null);
    localStorage.setItem("token", null);
  };

  getAuthorization = () => {
    let authorization;
    if (this.state.user !== null) {
      authorization = this.state.tokenPrefix + this.state.token;
    }
    return authorization;
  };

  render() {
    return (
      <div>
        <main>
          {this.state.isAuthenticated ? (
            <>
              <Switch>
                <Route
                  exact
                  path={["/", "/help-desk"]}
                  render={props => (
                    <HelpDesk
                      {...props}
                      user={this.state.user}
                      handleLogOut={this.handleLogOut}
                      getAuthorization={this.getAuthorization}
                    />
                  )}
                />

                <Route
                  exact
                  path="/help-desk/ticket/:id"
                  render={props => (
                    <Ticket
                      {...props}
                      user={this.state.user}
                      getAuthorization={this.getAuthorization}
                    />
                  )}
                />

                <Route
                  exact
                  path="/help-desk/create-ticket"
                  render={props => (
                    <CreateTicket
                      {...props}
                      user={this.state.user}
                      getAuthorization={this.getAuthorization}
                    />
                  )}
                />
                <Route
                  exact
                  path="/help-desk/ticket/:id/edit"
                  render={props => (
                    <EditTicket
                      {...props}
                      user={this.state.user}
                      getAuthorization={this.getAuthorization}
                    />
                  )}
                />

                <Route
                  exact
                  path="/help-desk/ticket/:id/feedback/leave"
                  render={props => (
                    <TicketFeedbackLeave
                      {...props}
                      user={this.state.user}
                      getAuthorization={this.getAuthorization}
                    />
                  )}
                />
                <Route
                  exact
                  path="/help-desk/ticket/:id/feedback/view"
                  render={props => (
                    <TicketFeedbackView
                      {...props}
                      user={this.state.user}
                      getAuthorization={this.getAuthorization}
                    />
                  )}
                />
                <Route
                  exact
                  path={["/logout", "/login"]}
                  render={props => (
                    <LogOut
                      {...props}
                      user={this.state.user}
                      handleLogOut={this.handleLogOut}
                    />
                  )}
                />
                <Route path="/*" component={NotFound} />
              </Switch>
            </>
          ) : (
            <>
              <Switch>
                <Route
                  exact
                  path={["/", "/login", "/help-desk", "/*"]}
                  render={props => (
                    <LogIn {...props} handleLogIn={this.handleLogIn} />
                  )}
                />
              </Switch>
            </>
          )}
        </main>
      </div>
    );
  }
}
export default App;
