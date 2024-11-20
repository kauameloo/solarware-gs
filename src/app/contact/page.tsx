import ContactForm from "@/components/ContactForm";

export default function ContactPage() {
  return (
    <div className="max-w-2xl mx-auto">
      <h1 className="text-3xl font-bold mb-6 text-gray-800 dark:text-white">
        Entre em Contato
      </h1>
      <ContactForm />
    </div>
  );
}
