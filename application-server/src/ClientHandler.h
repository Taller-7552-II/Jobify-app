#ifndef CLIENTHANDLER_H
#define CLIENTHANDLER_H

#include <sstream>
#include <string>
#include "../src/3rdparty/mongoose/mongoose.h"
#include <mutex>

#define DEFAULT_PORT 8000
#define POLL_MILISECONDS 1000

class ClientHandler {
    public:
        ClientHandler();
	
        virtual ~ClientHandler();

        bool isRunning();
	
        void run();

        void stop();
    
    private:
        static void eventHandler(struct mg_connection* connection, int event, void* eventData);
        bool running;
        struct mg_mgr manager;
        struct mg_connection* connection;
        std::mutex mtx;
};

#endif // CLIENTHANDLER_H