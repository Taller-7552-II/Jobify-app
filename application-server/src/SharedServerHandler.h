#ifndef SHAREDSERVERHANDLER_H_
#define SHAREDSERVERHANDLER_H_

#include "../src/3rdparty/mongoose/mongoose.h"
#include <string>
#include <mutex>
using namespace std;

#define DEFAULT_POLL_SLEEPTIME 1000

class SharedServerHandler {
public:
	SharedServerHandler();

	virtual ~SharedServerHandler();


	//todo Implementar los PUT, GET, POST, DELETE de acuerdo a lo solicitado
	//Usaran la siguiente función

	/* ConnectToUrl
	 *
	 * url: URL al cual intentara conectarse
	 *
	 * postData: si es NULL, se crea un GET request. En caso contrario se crea un POST request
	 * 			 con los datos pasados como parametro (se debe pasar los extra header correspondientes).
	 */
	void connectToUrl(const std::string& url, const char* extraHeader, const char* postData);

    bool isWaiting();

    bool isRunning();

    void run();

    void stop();

private:

	//maximo tiempo que puede dormir entre polls, en milisegundos
	int m_pollTime;

    //variables de mangoose
    struct mg_mgr m_manager; //Se usa si va con Mongoose
    struct mg_connection *m_connection;
    std::mutex m_mtx;

    //variables estaticas que se usan en el event handler
    static void eventHandler(struct mg_connection* connection, int event, void* eventData);
    static int s_exit_flag;
    static std::string s_url;

    bool running;

    void printConnectingMsg();

};

#endif /* SHAREDSERVERHANDLER_H_ */
