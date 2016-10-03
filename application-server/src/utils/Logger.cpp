#include "Logger.h"
//#include <boost/filesystem.hpp>

Logger* Logger::s_pInstance = 0;

Logger::Logger()
{
	pthread_mutex_init(&m_logMutex, NULL);
    m_debugAvailable = true;
    m_warningAvailable = true;
    m_errorAvailable = true;
    m_infoAvailable = true;

   /* if(!(boost::filesystem::exists("Logfiles/")))
    {
    	boost::filesystem::create_directory("Logfiles/");
    }*/
    int permissionMode = 0700;
    createlogfilesDirectory(permissionMode);
	std::string filename = generateFilename();

	m_file.open( filename.c_str(), std::ios::out|std::ios::in|std::ios::app );
	 if (!m_file.good())
	 {
		 perror("WARNING: El Logger no pudo abrir el archivo ");
		 perror (filename.c_str());
		 perror (".\n");
		 return;
	 }
	 m_file << "  ===============================================\n"
	          << "    Comienzo del Log ( "
	          << Time::getDate()
	          << " a las "
	          << Time::getTime()
	          << " ) \n  ===============================================\n\n";
	 m_file.flush();
}

Logger::~Logger()
{
  if (!m_file.good()) return;
  m_file << "\n  ===============================================\n"
        << "    Finaliza el Log ( "
        << Time::getDate()
        << " a las "
        << Time::getTime()
        << " ) \n  ===============================================\n\n";
  m_file.flush();
  pthread_mutex_destroy(&m_logMutex);
  m_file.close();
}

void Logger::createlogfilesDirectory(const int permissionMode)
{
    struct stat st = {0};

    if (stat("Logfiles/", &st) == -1) {
        mkdir("Logfiles/", permissionMode);
    }
}

 void Logger::Log(const std::string& message, LogType logLevel)
 {
	 pthread_mutex_lock(&m_logMutex);
	 switch(logLevel)
	 {
	 case LOGTYPE_DEBUG:
		 if (!m_debugAvailable)
			 return;
		 m_file << "  DEBUG: ";
		 break;

	 case LOGTYPE_WARN:
		 if (!m_warningAvailable)
			 return;
		 m_file << "  WARNING: ";
		 break;

	 case LOGTYPE_ERROR:
		 if (!m_errorAvailable)
			 return;
		 m_file << "  ERROR: ";
		 break;

	 case LOGTYPE_INFO:
		 if (!m_infoAvailable)
			 return;
		 m_file << "  INFO: ";
		 break;

	 default:
		 break;
	 }

	 m_file << message << "\n";
	 m_file.flush();
	pthread_mutex_unlock(&m_logMutex);

 }

 void Logger::setLoglevel(bool info, bool warn, bool errors, bool debug)
 {
	 m_debugAvailable = debug;
	 m_infoAvailable = info;
	 m_warningAvailable = warn;
	 m_errorAvailable = errors;
 }

 void Logger::Close()
 {
	 delete s_pInstance;
	 s_pInstance = 0;
 }

string Logger::generateFilename()
{
	 std::stringstream ss;
	 ss << "Logfiles/logfile " << Time::getDate() << ".log";
	 return ss.str();
}
