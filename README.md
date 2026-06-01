# 📱 Native Android Coursera Clone with Real-Time Chat (Java)

A feature-rich, native Android mobile application developed as a comprehensive final project for my university Mobile Application Development course. Built entirely in **Java using Android Studio**, this app showcases native hardware control, cloud AI ecosystem connectivity, and a live, synced backend powered by Firebase.

---

## 🛠️ Advanced Technical Integrations

* **Firebase Real-Time Global Chat:** Engineered a live, bidirectional chat network connecting all app users on a single global channel. Messages sync instantaneously using listener data streams. The UI dynamically parses the active user session, anchoring the current user's messages to the right side of the layout and incoming peer messages to the left.
* **Cloud Authentication (Firebase Auth):** Implemented a secure, cloud-hosted **Sign Up** and **Login** gateway utilizing the Firebase Authentication database to safely register, validate, and persist user identity states.
* **Gemini AI Integration:** Successfully integrated Google's Gemini API to power an intelligent, responsive in-app assistant or learning helper, handling asynchronous network requests smoothly to deliver real-time AI responses.
* **Hardware QR Code Scanner:** Implemented a native QR code scanning subsystem, allowing the app to interact seamlessly with physical device cameras to scan, decode, and parse data on the fly.
* **Monetization Framework:** Integrated Google Mobile Ads SDK to implement functional **Google Test Ads**, proving core competency in mobile ad placement, lifecycle management, and monetization layouts.
* **Native UI Data Architectures:** Leveraged foundational Android UI components like **RecyclerView** and **ListView** paired with custom adapters to achieve highly optimized, smooth-scrolling live data lists that efficiently manage device memory.

---

## 🏗️ Tech Stack & Environment

* **Development IDE:** Android Studio
* **Core Language:** Java (Native Android SDK)
* **Backend Ecosystem:** Firebase (Realtime Database & Authentication)
* **API Systems:** Google Gemini Developer API
* **Hardware APIs:** Android CameraX / ZXing Barcode Scanning Library
* **Ad Network:** Google AdMob SDK (Test Environments)

---

## 💡 Engineering Takeaways

Developing this application pushed me past basic user interfaces and required managing complex mobile ecosystems:
1. **Real-Time Data Streams:** Implementing active synchronization listeners to process live message payloads without manual user refreshes.
2. **Conditional View Rendering:** Writing structural logic inside custom Adapters to format chat UI bubbles uniquely based on the active user’s unique ID.
3. **Asynchronous API Communication:** Learning how to process external AI model responses without blocking the main UI thread.
4. **Device Hardware Control:** Requesting user permissions and interacting safely with mobile camera hardware for quick scanning workflows.
