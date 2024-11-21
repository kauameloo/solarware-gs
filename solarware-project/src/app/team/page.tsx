import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Avatar, AvatarFallback, AvatarImage } from "@/components/ui/avatar";
import { Github, Linkedin } from "lucide-react";

const teamMembers = [
  {
    name: "Kauã de Melo Rodrigues",
    rm: "555168",
    turma: "1TDSPO",
    photo: "/images/kaua.png",
    github: "https://github.com/kauameloo/",
    linkedin: "https://www.linkedin.com/in/kaua-rodrigues01/",
  },
  {
    name: "Caike Dametto",
    rm: "558614",
    turma: "1TDSPO",
    photo: "/images/caike.jpeg",
    github: "https://github.com/",
    linkedin: "https://linkedin.com/in/",
  },
  {
    name: "Guilherme Janunzzi",
    rm: "558461",
    turma: "1TDSPO",
    photo: "/images/gui.jpeg",
    github: "https://github.com/",
    linkedin: "https://linkedin.com/in/",
  },
];

export default function TeamPage() {
  return (
    <div>
      <h1 className="text-3xl font-bold mb-6 text-gray-800 dark:text-white">
        Nossa Equipe
      </h1>
      <div className="grid gap-6 md:grid-cols-2 lg:grid-cols-3">
        {teamMembers.map((member, index) => (
          <Card key={index}>
            <CardHeader>
              <Avatar className="w-24 h-24 mx-auto">
                <AvatarImage src={member.photo} alt={member.name} />
                <AvatarFallback>
                  {member.name
                    .split(" ")
                    .map((n) => n[0])
                    .join("")}
                </AvatarFallback>
              </Avatar>
              <CardTitle className="text-center mt-2">{member.name}</CardTitle>
            </CardHeader>
            <CardContent className="text-center">
              <p className="text-sm text-gray-500 dark:text-gray-400">
                RM: {member.rm}
              </p>
              <p className="text-sm text-gray-500 dark:text-gray-400">
                Turma: {member.turma}
              </p>
              <div className="flex justify-center space-x-4 mt-4">
                <a
                  href={member.github}
                  target="_blank"
                  rel="noopener noreferrer"
                  className="text-gray-600 hover:text-black dark:text-gray-400 dark:hover:text-white"
                >
                  <Github className="h-6 w-6" />
                </a>
                <a
                  href={member.linkedin}
                  target="_blank"
                  rel="noopener noreferrer"
                  className="text-blue-600 hover:text-blue-800 dark:text-blue-400 dark:hover:text-blue-300"
                >
                  <Linkedin className="h-6 w-6" />
                </a>
              </div>
            </CardContent>
          </Card>
        ))}
      </div>
    </div>
  );
}
