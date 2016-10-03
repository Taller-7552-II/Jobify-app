#include "ClientHandler.h"
#include <string>
#include <iostream>

using namespace std;

ClientHandler::ClientHandler(){
	mg_mgr_init(&manager, NULL);
    string port = to_string(DEFAULT_PORT);
	cout << "Starting server on port " << port <<"\n";
	connection = mg_bind(&manager, port.c_str(), eventHandler);
    if (connection == NULL){
    	cout <<"Failed to create listener\n";
    	running = false;
    }
    mg_set_protocol_http_websocket(connection);
    //mg_enable_multithreading(connection);
    running = true;
}

ClientHandler::~ClientHandler() {
   mg_mgr_free(&manager);
}

bool ClientHandler::isRunning(){
	return ClientHandler::running;
}

void ClientHandler::eventHandler(struct mg_connection* connection, int event, void* eventData) {
	
	struct http_message *httpMsg = (struct http_message *) eventData;

	switch (event) {
		case MG_EV_HTTP_REQUEST:
			mg_send_head(connection, 200, httpMsg->message.len, "Content-Type: text/plain");
			mg_printf(connection, "%.*s", httpMsg->message.len, httpMsg->message.p);
			break;
		default:
			break;
	}
}


void ClientHandler::run() {
	while(running)
		mg_mgr_poll(&manager, POLL_MILISECONDS);	
}

void ClientHandler::stop(){
	mtx.lock();
	running = false;
	mtx.unlock();
}
