#include "Server.h"
#include <thread>
#include <mutex>
#include <iostream>

using namespace std;

#define OVER_KEY "exit"


void isItOver(Server& server, mutex& condition_mutex){
	string s;
	while (s.compare(OVER_KEY) != 0) {
		cin >> s;
	}
	condition_mutex.lock();
	server.stop();
	condition_mutex.unlock();
}

int main(){
	//Setup
	Server* server = new Server();

	mutex over_mutex;
	thread over_thread(isItOver, ref(*server), ref(over_mutex));

	//
	if (server->isReady())
		server->run();

	over_thread.join();	
	delete server;

}
